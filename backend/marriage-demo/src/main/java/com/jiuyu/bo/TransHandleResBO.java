package com.jiuyu.bo;

import java.io.Serializable;

/**
 * <p>Title: TransHandleResBO</p>
 * <p>Description: </p>
 * @author he_jiebing@jiuyv.com
   @date   2021年4月24日 下午7:55:11
 */

public class TransHandleResBO implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1667864439871913556L;
	
	
	private TransDataRespBO data;

	public TransDataRespBO getData() {
		return data;
	}

	public void setData(TransDataRespBO data) {
		this.data = data;
	}
	
	

}
