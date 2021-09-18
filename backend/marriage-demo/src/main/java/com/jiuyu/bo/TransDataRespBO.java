package com.jiuyu.bo;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title: TransDataRespBO</p>
 * <p>Description: </p>
 * @author he_jiebing@jiuyv.com
   @date   2021年4月24日 下午7:56:09
 */

public class TransDataRespBO implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5037579102720199362L;
	
	private String transactionHash;
	
	private List<LogAddressBO> logs;

	public String getTransactionHash() {
		return transactionHash;
	}

	public void setTransactionHash(String transactionHash) {
		this.transactionHash = transactionHash;
	}

	public List<LogAddressBO> getLogs() {
		return logs;
	}

	public void setLogs(List<LogAddressBO> logs) {
		this.logs = logs;
	}
	
	

}
