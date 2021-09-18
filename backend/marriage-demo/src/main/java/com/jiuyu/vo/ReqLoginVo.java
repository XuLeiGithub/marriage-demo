package com.jiuyu.vo;

import java.io.Serializable;

/**
 * <p>Title: ReqLoginVo</p>
 * <p>Description: </p>
 * @author he_jiebing@jiuyv.com
   @date   2021年8月19日 下午10:07:05
 */

public class ReqLoginVo implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8112832851657681932L;

	private String username;
	
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
