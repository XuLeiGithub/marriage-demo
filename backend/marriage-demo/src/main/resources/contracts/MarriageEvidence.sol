pragma solidity^0.4.25;
import "./EvidenceFactory.sol";
import "./Character.sol";

contract MarriageEvidence{
    address private admin;
    mapping(address=>bool) private isMarried; //角色是否已经结婚，避免重婚
    mapping(address=>bool) private isSigned;  //证书中角色是否已经签字
    mapping(address=>bool) private eviSigned; //证书中成员是否均已经达成签字，全部签字为true,非全部均为false
    string private certificateNumber; //证书编号
    mapping(string=>address) private characterAddress; //角色合约地址
    mapping(string=>address) private eviContractAddress;//存证合约地址
    mapping(string=>address) private eviAddress;//证书地址
    
    constructor() public {
        admin = msg.sender;
    }
    
    modifier adminOnly{  
        require(msg.sender == admin ,"require admin");
        _;
    }
    
    modifier checkMarried(address _husband,address _wife){
        require(isMarried[_husband] == false,"Husband is married");
        require(isMarried[_wife] == false,"Wife is married");
        _;
    }
    

    function deployEvi(string _certificateNumber,address _husband,string _husbandSummary,address _wife,string _wifeSummary,address _witness,bytes32 _witnessName) external adminOnly checkMarried(_husband,_wife){
        Character cha = new Character();
        characterAddress[_certificateNumber]= address(cha);
        cha.addCharacter(msg.sender,"民政局");
        cha.addCharacter(_husband,_husbandSummary);
        cha.addCharacter(_wife,_wifeSummary); 
        cha.addCharacter(_witness,bytes32ToString(_witnessName));
        EvidenceFactory evi = new EvidenceFactory(cha.getAllCharater());
        eviContractAddress[_certificateNumber]=address(evi);
    }
    
    function newEvi(string _certificateNumber,string _evi)external adminOnly returns(bool){
        eviAddress[_certificateNumber]=EvidenceFactory(eviContractAddress[_certificateNumber]).newEvidence(_evi);
        isSigned[msg.sender] = true;
        return true;
    }
    
    function sign(string _certificateNumber) external returns(bool) {
            require(EvidenceFactory(eviContractAddress[_certificateNumber]).verify(msg.sender),"you not is signer");
            isSigned[msg.sender] = EvidenceFactory(eviContractAddress[_certificateNumber]).addSignatures(eviAddress[_certificateNumber]);
            return isSigned[msg.sender];
    }
    
    function checkSigned(string _certificateNumber)internal returns(bool){
        address[] memory characters;
        characters = Character(characterAddress[_certificateNumber]).getAllCharater();
        for(uint256 i=0;i<characters.length;i++){
            if(isSigned[characters[i]] == false){
                return false;
            }
        }
        return true;
    }
    
    function checkEviSigned(string _certificateNumber)public constant 
        returns(bool){
            if(checkSigned(_certificateNumber) == true){
                eviSigned[eviAddress[_certificateNumber]] = true;
                isMarried[Character(characterAddress[_certificateNumber]).getAllCharater()[1]] = true;
                isMarried[Character(characterAddress[_certificateNumber]).getAllCharater()[2]] = true;
            }
        return eviSigned[eviAddress[_certificateNumber]];
    }
    
    function getEvi(string _certificateNumber) public constant 
        returns(string,address[],address[]){
        return EvidenceFactory(eviContractAddress[_certificateNumber]).getEvidence(eviAddress[_certificateNumber]);
    }
    
    function getEviCharacter(string _certificateNumber) public constant 
        returns(address,string,address,string,address,string){
            address Husband;
            address Wife;
            address Witness;
            address[] memory characters;
            string memory HusbandSummary;
            string memory WifeSummary;
            string memory WitnessSummary; 
            characters = Character(characterAddress[_certificateNumber]).getAllCharater();
            Husband = characters[1];
            Wife = characters[2];
            Witness = characters[3];
            HusbandSummary = Character(characterAddress[_certificateNumber]).seekCharacter(Husband);
            WifeSummary = Character(characterAddress[_certificateNumber]).seekCharacter(Wife);
            WitnessSummary = Character(characterAddress[_certificateNumber]).seekCharacter(Witness);
        return(Husband,HusbandSummary,Wife,WifeSummary,Witness,WitnessSummary);
    }
    
    function bytes32ToString(bytes32 x) constant internal returns(string){
        bytes memory bytesString = new bytes(32);
        uint charCount = 0 ;
        for(uint j = 0 ; j<32;j++){
            byte char = byte(bytes32(uint(x) *2 **(8*j)));
            if(char !=0){
                bytesString[charCount] = char;
                charCount++;
            }
        }
        bytes memory bytesStringTrimmed = new bytes(charCount);
        for(j=0;j<charCount;j++){
            bytesStringTrimmed[j]=bytesString[j];
        }
        return string(bytesStringTrimmed);
    }

}