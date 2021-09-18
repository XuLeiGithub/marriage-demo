package com.jiuyu.vo;

import java.io.Serializable;

/**
 * <p>Title: ResAddUser</p>
 * <p>Description: </p>
 * @author he_jiebing@jiuyv.com
   @date   2021年8月19日 下午10:55:23
 */

public class ResAddUser implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private String txHash;

	public String getTxHash() {
		return txHash;
	}

	public void setTxHash(String txHash) {
		this.txHash = txHash;
	}
	
	

}
