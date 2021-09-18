package com.jiuyu.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 管理员信息表
 * 
 * @author hejiebing
 * @email he_jiebing@jiuyv.com
 * @date 2021-08-19 21:07:24
 */
@TableName("tbl_admin_user")
public class AdminUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 姓名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * signuserid
	 */
	private String signUserId;
	/**
	 * 公钥地址
	 */
	private String publicAddress;
	/**
	 * 插入时间
	 */
	private Date insertTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getSignUserId() {
		return signUserId;
	}
	public void setSignUserId(String signUserId) {
		this.signUserId = signUserId;
	}
	public String getPublicAddress() {
		return publicAddress;
	}
	public void setPublicAddress(String publicAddress) {
		this.publicAddress = publicAddress;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	

}
