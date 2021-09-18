package com.jiuyu.vo;

import java.io.Serializable;

/**
 * <p>Title: ReqAddUser</p>
 * <p>Description: </p>
 * @author he_jiebing@jiuyv.com
   @date   2021年8月19日 下午10:43:26
 */

public class ReqAddUser implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7338514675327325683L;
	
	private Long adminUserId;
	
	/**
	 * 姓名
	 */
	private String username;
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
	/**
	 * 家庭住址
	 */
	private String familyAddress;
	
	
	public Long getAdminUserId() {
		return adminUserId;
	}
	public void setAdminUserId(Long adminUserId) {
		this.adminUserId = adminUserId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getFamilyAddress() {
		return familyAddress;
	}
	public void setFamilyAddress(String familyAddress) {
		this.familyAddress = familyAddress;
	}
	
	

}
