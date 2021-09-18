package com.jiuyu.vo;

import java.io.Serializable;

/**
 * <p>Title: ResSignVo</p>
 * <p>Description: </p>
 * @author he_jiebing@jiuyv.com
   @date   2021年8月20日 上午11:10:29
 */

public class ResSignVo implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8468721294075037960L;
	
	private String txHash;

	public String getTxHash() {
		return txHash;
	}

	public void setTxHash(String txHash) {
		this.txHash = txHash;
	}
	
	

}
