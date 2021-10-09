# FISCO BCOS Block Chain Marray System
<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
[![All Contributors](https://img.shields.io/badge/all_contributors-2-orange.svg?style=flat-square)](#contributors-)
<!-- ALL-CONTRIBUTORS-BADGE:END -->
FISCO BCOS Block Chain Marry System created by Shanghai JiuYu Software Systems Co,Ltd.

由上海久誉软件系统有限公司研发的针对基于 FISCO BCOS 的登记结婚系统。

![](https://img.shields.io/badge/JiuYu-CopyRight-blue)
  ![](https://img.shields.io/badge/language-java-orange.svg)
  ![](https://img.shields.io/badge/license-MIT-000000.svg)


## 1. 设计理念

基于区块链的登记结婚系统是对结合Character角色合约与Evidence存证合约打造结婚证书的实现，利用WeBASE管理平台将合约导出为Java项目。



## 2. 目标用户

基于区块链的登记结婚系统目标用户是民政局工作人员。



## 3. 系统架构

![供应链金融](https://user-images.githubusercontent.com/11324122/123046786-1c671080-d42f-11eb-9683-ac407d338e22.jpg)

###  3.1 系统功能

![image](https://user-images.githubusercontent.com/85043867/136653002-48adebd0-5165-4331-bc02-015c951e2c4c.png)



###  3.2 具体业务层

  #### 新建证书流程图
![image](https://user-images.githubusercontent.com/85043867/136653037-4cae4a64-cc96-4874-ad1d-e34934981eaa.png)

  #### 用户签名流程图

![image](https://user-images.githubusercontent.com/85043867/136653063-bef8f221-c924-4275-bb5c-9f3756b3828f.png)



## 4. 使用WeBASE平台导出Java项目

### 4.1 上传合约

![image](https://user-images.githubusercontent.com/85043867/136653111-088e5c1c-292a-49b1-9ff9-9241f243c212.png)

合约源码：MarriageEvidence.sol

```java
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
```

<h5>合约源码来自：https://github.com/Blockchain-Key/Contract-instance</h5>



### 4.2 编译合约

![image](https://user-images.githubusercontent.com/85043867/136653141-f2d2c51e-a5ef-4cb2-961d-9dd3c1f4259a.png)



### 4.3 部署合约

![image](https://user-images.githubusercontent.com/85043867/136653150-54b06d7a-8384-4ee3-8bdd-4cbb11080a6f.png)



### 4.4 导出Java项目

选择节点、用户及需要导出的合约

![image](https://user-images.githubusercontent.com/85043867/136653193-00bdc546-419a-42be-a893-c3c73eaac0a1.png)

![image](https://user-images.githubusercontent.com/85043867/136653201-218102bb-3f41-4490-83d7-6470bbf1bd33.png)

### 4.5 查看目录结构

- mian目录下存放执行代码
- conf目录下存放节点证书配置

![image](https://user-images.githubusercontent.com/85043867/136653218-c40a766a-bbbe-49ed-97fe-9698b3cb47ac.png)


### 4.6 将Gradle项目转为Maven项目

![image](https://user-images.githubusercontent.com/85043867/136653234-31fef408-aaf2-4c76-b79e-14c2d4ad62b2.png)

### 4.7 导入Maven依赖 

```java
<properties>
        <mybatis.plus.version>3.4.0</mybatis.plus.version>
        <common.lang.version>2.6</common.lang.version>
        <common.lang3.version>3.10</common.lang3.version>
        <servlet.api.version>2.5</servlet.api.version>
        <hutool.version>5.3.10</hutool.version>
        <fastjson.version>1.2.68</fastjson.version>
        <hibernate-validator.version>6.1.0.Final</hibernate-validator.version>
    </properties>

    <repositories>
        <repository>
            <id>aliyun</id>
            <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
        </repository>

        <repository>
            <id>sonatype</id>
            <url>https://oss.sonatype.org/content/repositories/snapshots</url>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>com.webank</groupId>
            <artifactId>webase-app-sdk</artifactId>
            <version>1.5.1-SNAPSHOT</version>
        </dependency>

        <!-- json logstash encoder -->
        <dependency>
            <groupId>net.logstash.logback</groupId>
            <artifactId>logstash-logback-encoder</artifactId>
            <version>6.4</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!-- junit -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <scope>test</scope>
        </dependency>

        <!--面向切面 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>

        <!-- https://mvnrepository.com/artifact/com.jcraft/jsch -->
        <dependency>
            <groupId>com.jcraft</groupId>
            <artifactId>jsch</artifactId>
            <version>0.1.55</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/commons-io/commons-io -->
        <dependency>
            <groupId>commons-io</groupId>
            <artifactId>commons-io</artifactId>
            <version>2.6</version>
        </dependency>

        <!-- mybatis-plus -->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>${mybatis.plus.version}</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.apache.httpcomponents/httpcore -->
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpcore</artifactId>
        </dependency>

        <dependency>
            <groupId>commons-lang</groupId>
            <artifactId>commons-lang</artifactId>
            <version>${common.lang.version}</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.apache.commons/commons-lang3 -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
        </dependency>
        <dependency>
            <groupId>commons-codec</groupId>
            <artifactId>commons-codec</artifactId>
            <version>1.11</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/com.alibaba/fastjson -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>${fastjson.version}</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
            <version>${servlet.api.version}</version>
            <scope>provided</scope>
        </dependency>
        <!-- 引用redis -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>io.lettuce</groupId>
                    <artifactId>lettuce-core</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <dependency>
            <groupId>redis.clients</groupId>
            <artifactId>jedis</artifactId>
        </dependency>

        <!-- https://mvnrepository.com/artifact/cn.hutool/hutool-all -->
        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
            <version>${hutool.version}</version>
            <exclusions>
                <exclusion>
                    <groupId>org.apache.poi</groupId>
                    <artifactId>poi</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>org.apache.poi</groupId>
                    <artifactId>poi-ooxml</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>org.apache.poi</groupId>
                    <artifactId>poi-ooxml-schemas</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.hibernate/hibernate-validator -->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-validator</artifactId>
            <version>${hibernate-validator.version}</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/javax.validation/validation-api -->
        <dependency>
            <groupId>javax.validation</groupId>
            <artifactId>validation-api</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!--面向切面 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-json</artifactId>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.bouncycastle/bcprov-jdk15on -->
        <dependency>
            <groupId>org.bouncycastle</groupId>
            <artifactId>bcprov-jdk15on</artifactId>
            <version>1.67</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp -->
        <dependency>
            <groupId>com.squareup.okhttp3</groupId>
            <artifactId>okhttp</artifactId>
            <version>4.8.1</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.11.0</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-jdk8 -->
        <dependency>
            <groupId>com.fasterxml.jackson.datatype</groupId>
            <artifactId>jackson-datatype-jdk8</artifactId>
            <version>2.11.0</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-jsr310 -->
        <dependency>
            <groupId>com.fasterxml.jackson.datatype</groupId>
            <artifactId>jackson-datatype-jsr310</artifactId>
            <version>2.11.0</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-parameter-names -->
        <dependency>
            <groupId>com.fasterxml.jackson.module</groupId>
            <artifactId>jackson-module-parameter-names</artifactId>
            <version>2.11.0</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.16</version>
            <scope>provided</scope>
        </dependency>

        <dependency>
            <groupId>org.fisco-bcos.java-sdk</groupId>
            <artifactId>fisco-bcos-java-sdk</artifactId>
            <version>2.7.2</version>
        </dependency>

        <dependency>
            <groupId>com.webank</groupId>
            <artifactId>webase-app-sdk</artifactId>
            <version>1.5.1-SNAPSHOT</version>
        </dependency>

    </dependencies>
```



## 5. 程序运行

### 5.1 前置工作
首先需要搭建FISCO BCOS链与WeBASE服务，本案例通过WeBASE一键部署搭建4节点的链和WeBASE服务

参考WeBASE官方提供的[文档](https://webasedoc.readthedocs.io/zh_CN/latest/docs/WeBASE/install.html)，详情请参见：https://webasedoc.readthedocs.io/zh_CN/latest/docs/WeBASE/install.html
- **注**：部署WeBASE时注意修改一键部署的配置文件`common.properties`中修改`node.counts=4`来搭建4节点的链
  
### 5.2 依赖安装

#### maven安装
```Bash
# 下载maven包
wget https://mirrors.bfsu.edu.cn/apache/maven/maven-3/3.8.1/binaries/apache-maven-3.8.1-bin.tar.gz
# 解压
tar -zxvf apache-maven-3.8.1-bin.tar.gz
# 重命名
mv apache-maven-3.8.1-bin maven
# 查看当前路径
cd maven/
pwd
	/data/home/webase/maven

# 配置mvn环境变量
vi /etc/profile 

	export MAVEN_HOME=/data/home/webase/maven
	export PATH=$PATH:$MAVEN_HOME/bin

# 更新环境变量
source /etc/profile 
# 检查mvn命令，正常输出版本号则安装成功
mvn -v
```

#### npm安装
```Bash
# 下载node包
wget https://npm.taobao.org/mirrors/node/v10.16.2/node-v10.16.2-linux-x64.tar.gz
# 解压
tar -zxvf node-v10.16.2-linux-x64.tar.gz
# 重命名
mv node-v10.16.2-linux-x64 node
# 查看路径
cd node/
pwd
	/data/home/webase/node

# 配置node环境变量
vi /etc/profile 

	export NODE_HOME=/data/home/webase/node
	export PATH=$PATH:$NODE_HOME/bin

# 更新环境变量
source /etc/profile 

# 检查npm命令，正常输出版本号则安装成功
npm -v
```

#### mysql创建数据库

以mysql用户为root为例，创建`marriage_demo`的数据库
```
mysql -uroot -p -e "create database marriage_demo"
```

### 5.3 拉取代码

1 拉取代码

```
git clone https://github.com/jiuyu-software/marriage-demo
```

项目源码中包含`frontend`前端代码和`backend`后端代码，目录结构如下

```Bash
cd marriage-demo/
ls
```

```Bash
├── backend
│   └── marriage-demo
├── frontend
│   └── marriage
└── README.md
```


### 5.4 前端代码部署
前端代码基于VUE编写

#### 1 修改配置文件
进入`frontend/marriage`目录，修改配置文件`vue.config.js`中`proxy`，连接上文的backend后端服务

```Bash
cd frontend/marriage
vi vue.config.js
```

```Bash
# 以同机运行为例，后端服务同机运行，IP为127.0.0.1，端口为8080
# 修改proxy.target的值为对应的IP:PORT
    proxy: {
      // change xxx-api/login => mock/login
      // detail: https://cli.vuejs.org/config/#devserver-proxy
      [process.env.VUE_APP_BASE_API]: {
//        target: "http://**.**.**.**:**",
        target: "http://127.0.0.1:8080",
        changeOrigin: true,
        pathRewrite: {
          ["^" + process.env.VUE_APP_BASE_API]: "",
        },
      },
    },

```

#### 2 编译并运行
```Bash
# 安装前端依赖包
npm install
# 运行
npm run dev
# 运行成功后显示
  App running at:
    - Local:   http://localhost:9528 
    - Network: http://127.0.0.2:9528 # 127.0.0.2为内网或公网IP

    Note that the development build is not optimized.
    To create a production build, run npm run build.
```

我们可以通过在浏览器中访问`http://127.0.0.2:9528`即可访问
- **此处浏览器访问的URL和下文后端服务中配置的appLink需保持一致**
- 如果通过`npm build`构建静态文件 + nginx重定向的方式加载前端，需要修改nginx.conf中访问后端服务的IP端口，并对外暴露9528端口即可。
- 需要部署下文的后端服务后，才可正常操作页面。具体操作步骤可以参考下文的[运行演示](#demo)

### 5.5 后端代码部署
后端代码是基于SpringBoot工程

#### 1 执行sql脚本

```Bash
cd backend/marriage-demo/src/main/resources/db
# 通过mysql -e命令执行.sql脚本，以root用户，db名为marriage_demo为例
mysql -uroot -p  -D supplychain -e "source ./marriage_demo.sql"
```
#### 2 部署MarriageEvidence合约

```Bash
cd backend/marriage-demo/src/main/resources/contracts/MarriageEvidence.sol
cat MarriageEvidence.sol
```

将合约内容复制记录，随后到WeBASE中部署

**进入WeBASE管理台**
- 创建WeBASE私钥：在“私钥管理”中创建一个新的私钥用户，记录其signUserId，如：d0fb7d6c9fa04ef484e10f4bf5b34426
- WeBASE的“合约管理-合约IDE”中，创建MarriageEvidence的合约，粘贴上文的MarriageEvidence.sol内容，并编译，部署合约，记录合约地址，如：0xbbac4362f59a8ffe78ef4585460e9236c02b6c48

#### 3 WeBASE应用接入
点击“应用管理”，若该案例已集成在WeBASE，则选择模板，在注册信息里面可获得WeBASE-Node-Manager的 `IP,Port,appKey,appSecret` 相关信息，记录这些信息，在下文的的application.properties配置中会用到
![image](https://user-images.githubusercontent.com/85043867/136653281-064d765d-1ca6-48e0-8327-2b23d2f3a371.png)

#### 4 修改application.properties文件

```Bash
cd backend/marriage-demo/src/main/resources/application.properties
vi application.properties
```

- 修改配置文件的mysql连接配置
- 修改WeBASE-Front和WeBASE-Node-Manager的配置
- 修改WeBASE节点地址端口
- 修改本案例的前端访问URL配置
- 修改MarriageEvidence合约的配置

```Bash
spring.application.name=marriage-demo
# WeBASE 节点地址及端口
system.peers=127.0.0.1:20200
system.groupId=1
# WeBASE 节点证书配置目录
system.certPath=conf,config,src/main/resources/conf,src/main/resources/config
# MarriageEvidence 合约部署用户
system.hexPrivateKey=f7ee38ca3715130c164f140ef5751828a0aa8aab60198d26df4e7fd26d843233
# MarriageEvidence.sol 合约部署地址
system.contract.marriageEvidenceAddress=0x62a25adcae9f0f9ce22caa35bc6fb596c8476f71

# 服务默认端口，若修改，需要在前端访问后端时对应修改
server.port=8080
server.session.timeout=60
banner.charset=UTF-8

# 数据库相关信息
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/marriage_demo
spring.datasource.type=com.zaxxer.hikari.HikariDataSource
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.minimum-idle=10
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.auto-commit=true
spring.datasource.hikari.idle-timeout=600000
spring.datasource.hikari.pool-name=DateSourceHikariCP
spring.datasource.hikari.max-lifetime=1800000
spring.datasource.hikari.connection-test-query=SELECT 1
# webase-node-mgr的IP与端口
webase.node.mgr.url=http://127.0.0.1:5001
# webase管理台-应用管理，创建自定义应用生成的appKey
webase.node.mgr.appKey=kkCbsvFG
# webase管理台-应用管理，创建自定义应用生成的appSecret
webase.node.mgr.appSecret=RcMHpkp5nVWSgFxsTtzLkE79pKZvvUMV
# 是否加密传输
webase.node.mgr.isTransferEncrypt=true

#pagehelper分页插件配置
pagehelper.helperDialect=mysql
pagehelper.reasonable=true
pagehelper.supportMethodsArguments=true
pagehelper.params=count=countSql
# page-size-zero：默认值为 false，当该参数设置为 true 时，如果 pageSize=0 或者 RowBounds.limit = 0 就会查询出全部的结果
#pagehelper.page-size-zero=true

# mybatis-plus 配置
mybatis-plus.mapper-locations=classpath:/mapper/**/*.xml

# 日志配置
logging.config=classpath:logback-boot.xml

# 打印日志级别
logging.level.root=DEBUG

# 返回json的全局时间格式
spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
spring.jackson.time-zone=GMT+8

```

#### 5 编译打包

编译项目，回到`backend/marriage-demo`目录，目录下有`pom.xml`的maven配置文件
```Bash
cd backend/marriage-demo
# maven 编译
mvn clean package -Dmaven.test.skip=true
```
打包完成后会得到`target`目录
- 若修改了`application.properties`，需要重新打包并运行

#### 6 运行

运行得到的jar
```Bash
cd target/
nohup java -jar marrage-demo-0.0.1-SNAPSHOT.jar &
```

查看日志
```Bash
tail -f logs/log/marriage-demo.log
```

当我们看到日志持续输出日志时即运行成功，就可以访问上文中部署的前端页面(`{IP}:9528`)进行操作了



## 6.运行演示

<span id="demo"></span>
演示地址: 基于区块链的供应链支付结算管理平台： http://marriage.icoding721.com		账户密码:admin/123456

WeBASE管理平台：http://supplychain.icoding721.com:5000/  		账密：**admin/Abcd12345**

演示登记结婚及签名过程
#### 6.1 进入首页
![image](https://user-images.githubusercontent.com/85043867/136653375-fdc54f99-7e5e-4ccc-bb69-6a16caf00959.png)

#### 6.2 管理员登录
![image](https://user-images.githubusercontent.com/85043867/136653390-3508bfe3-5e6b-4b80-88b2-ecb5f0c14bbb.png)


#### 6.3 新建用户并添加用户信息
![image](https://user-images.githubusercontent.com/85043867/136653396-31fe8490-9edb-448d-9d29-ddbf96a7f119.png)

注：需要添加男方、女方、证婚人三方用户信息

#### 6.4 新建证书
在证书列表中，点击新建证书

![image](https://user-images.githubusercontent.com/85043867/136653404-76b02168-da92-445f-b8d4-7f2e52172a4f.png)

在列表中，选择男方、女方及证婚人，点击提交

![image](https://user-images.githubusercontent.com/85043867/136653413-3cba1d6a-4bca-426c-b39d-6975f87938d8.png)

注：提交后证书状态为草稿状态，需各方完成签名后，就改为已确认状态。

![image](https://user-images.githubusercontent.com/85043867/136653424-61d62c8e-c652-47ee-b4fe-e7e75cd6674d.png)

#### 6.5 用户签名

在用户信息列表中找到需要签名的用户，点击签名操作，签名成功返回交易Hash

![image](https://user-images.githubusercontent.com/85043867/136653432-0eb4bc82-c642-4f4f-ab66-d7394e073403.png)

注：男方、女方、证婚人都需完成签名

<h4>6.6 查看证书</h4>

在证书列表中，可以查看证书详情及签名状态

![image](https://user-images.githubusercontent.com/85043867/136653449-94e0d962-7006-4119-8e11-94c7c6272273.png)

#### 6.7 查看WeBASE管理平台交易情况
![image](https://user-images.githubusercontent.com/85043867/136653454-c5a29936-29b5-4f2b-8b43-0d8a27e9f89e.png)



## 7.接入WeBASE过程介绍

### 7.1 WeBASE接入原理

本案例通过引入`webase-app-sdk`，并在项目的application.properties传入WeBASE应用管理中生成的配置信息即可。

##### 1 引入webase-app-sdk
本案例使用WeBASE提供的应用接入SDK`webase-app-sdk`接入WeBASE，在本案例的springboot pom.xml文件中已经集成
```
<dependency>
	<groupId>com.webank</groupId>
	<artifactId>webase-app-sdk</artifactId>
	<version>1.5.1-SNAPSHOT</version>
</dependency>
```

通过该SDK，我们可以在项目中调用WeBASE的接口，进行合约和私钥等数据的托管。如下文所示

##### 2 获取WeBASE应用接入配置
我们可以登录WeBASE 管理平台获取应用接入的配置信息

点击“应用管理”，若该案例已集成在WeBASE，则选择模板，在注册信息里面可获得IP,Port,appKey,appSecret 相关信息，拿到这些信息会放到java配置文件本案例中的application.properties
![image](https://user-images.githubusercontent.com/85043867/136653471-6acd28ed-da2c-4e82-bd0f-9b6ce2858012.png)

在配置了上述信息后，只要启动本案例的前后端服务，即完成了供应链服务接入WeBASE的操作，在WeBASE的“应用管理”中访问本案例的管理页面

### 7.2通过WeBASE管理私钥与合约
WeBASE管理平台在私钥管理可以查看通过业务系统注册的用户相关信息
![image](https://user-images.githubusercontent.com/84694840/122888084-195b1a00-d374-11eb-9332-90b3db59c98c.png)



## Contributors

<table>
  <tr>
    <td align="center"><a href="https://github.com/freezehe"><img src="https://avatars.githubusercontent.com/u/11324122?s=64&v=4" width="100px;" alt=""/><br /><sub><b>皮卡丘的猫</b></sub></a><br /><a href="https://github.com/jiuyu-software/supply-chain-demo/commits?author=freezehe" title="Code">💻</a></td>
    <td align="center"><a href="https://github.com/shitou13"><img src="https://avatars.githubusercontent.com/u/20125300?s=64&v=4" width="100px;" alt=""/><br /><sub><b>shitou</b></sub></a><br /><a href="https://github.com/jiuyu-software/supply-chain-demo/commits?author=shitou13" title="Code">💻</a></td>
    <td align="center"><a href="https://github.com/codingcattwo"><img src="https://avatars.githubusercontent.com/u/28867867?v=4" width="100px;" alt=""/><br /><sub><b>codingcattwo</b></sub></a><br /><a href="https://github.com/jiuyu-software/supply-chain-demo/commits?author=codingcattwo" title="Code">💻</a></td>
  </tr>
</table>

### 总结
此登记结婚案例是基于fisco bcos平台开发的一个比较简单的DApp，后续扩展的功能包括 
#### 1.部分功能需优化(持续开发中....)
## 如有不足之处，还请各方不吝赐教。感谢~~
## 如有商业合作，可以邮件至：he_jiebing@jiuyv.com

