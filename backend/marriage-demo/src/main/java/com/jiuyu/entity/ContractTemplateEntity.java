package com.jiuyu.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 合约模板表
 * 
 * @author hejiebing
 * @email he_jiebing@jiuyv.com
 * @date 2021-08-19 21:07:24
 */
@TableName("tbl_contract_template")
public class ContractTemplateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 合约名称
	 */
	private String contractName;
	/**
	 * 合约abi
	 */
	private String contractAbi;
	/**
	 * 合约bin
	 */
	private String contractBin;
	/**
	 * 合约base64
	 */
	private String contractBase64;
	/**
	 * 合约地址
	 */
	private String contractAddress;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public String getContractAbi() {
		return contractAbi;
	}
	public void setContractAbi(String contractAbi) {
		this.contractAbi = contractAbi;
	}
	public String getContractBin() {
		return contractBin;
	}
	public void setContractBin(String contractBin) {
		this.contractBin = contractBin;
	}
	public String getContractBase64() {
		return contractBase64;
	}
	public void setContractBase64(String contractBase64) {
		this.contractBase64 = contractBase64;
	}
	public String getContractAddress() {
		return contractAddress;
	}
	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}
	
	

}
