package com.jiuyu.bo;

import java.io.Serializable;

/**
 * <p>Title: ReqAddCharacter</p>
 * <p>Description: </p>
 * @author he_jiebing@jiuyv.com
   @date   2021年8月20日 下午1:08:37
 */

public class ReqAddCharacter implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 9045015498215726648L;
	
	/**
	 * 姓名
	 */
	private String username;
	/**
	 * 家庭住址
	 */
	private String familyAddress;
	/**
	 * 身份证信息
	 */
	private String idCard;
	/**
	 * 年龄
	 */
	private Integer age;
	/**
	 * 性别
	 */
	private String sex;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFamilyAddress() {
		return familyAddress;
	}
	public void setFamilyAddress(String familyAddress) {
		this.familyAddress = familyAddress;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	

}
