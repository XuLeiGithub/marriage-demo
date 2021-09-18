package com.jiuyu.vo;

import java.io.Serializable;

/**
 * <p>Title: ResCreateMarriageEvidenceVo</p>
 * <p>Description: </p>
 * @author he_jiebing@jiuyv.com
   @date   2021年8月20日 上午9:52:09
 */

public class ResCreateMarriageEvidenceVo implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 800076333440519120L;
	
	private String txHash;

	public String getTxHash() {
		return txHash;
	}

	public void setTxHash(String txHash) {
		this.txHash = txHash;
	}
	
	

}
