package com.jiuyu.vo;

import java.io.Serializable;

/**
 * <p>Title: ResLoginVo</p>
 * <p>Description: </p>
 * @author he_jiebing@jiuyv.com
   @date   2021年8月20日 下午2:04:21
 */

public class ResLoginVo implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -504298366794258582L;

	/**
	 * 姓名
	 */
	private String username;
	
	private Long adminUserId;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(Long adminUserId) {
		this.adminUserId = adminUserId;
	}
	
	

}
