package com.jiuyu.vo;

import java.io.Serializable;

/**
 * <p>Title: ResIndexVo</p>
 * <p>Description: </p>
 * @author he_jiebing@jiuyv.com
   @date   2021年8月19日 下午10:29:21
 */

public class ResIndexVo implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8097283499291122524L;

	private String maleUsername;
	
	private String femaleUsername;
	
	private String marriageDesc;

	public String getMaleUsername() {
		return maleUsername;
	}

	public void setMaleUsername(String maleUsername) {
		this.maleUsername = maleUsername;
	}

	public String getFemaleUsername() {
		return femaleUsername;
	}

	public void setFemaleUsername(String femaleUsername) {
		this.femaleUsername = femaleUsername;
	}

	public String getMarriageDesc() {
		return marriageDesc;
	}

	public void setMarriageDesc(String marriageDesc) {
		this.marriageDesc = marriageDesc;
	}
	
	

}
