package com.jiuyu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 结婚证信息表
 *
 * @author hejiebing
 * @email he_jiebing@jiuyv.com
 * @date 2021-08-19 21:07:24
 */
@TableName("tbl_marriage_info")
public class MarriageInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.INPUT)
    private String certificateNumber;
    /**
     * 男方姓名
     */
    private String maleUsername;
    /**
     * 男方userid
     */
    private Long maleUserId;
    /**
     * 男方signuserid
     */
    private String maleSignUserId;
    /**
     * 男方公钥地址
     */
    private String malePublicAddress;
    /**
     * 女方姓名
     */
    private String femaleUsername;
    /**
     * 女方userid
     */
    private Long femaleUserId;
    /**
     * 女生signuserid
     */
    private String femaleSignUserId;
    /**
     * 女生公钥地址
     */
    private String femalePublicAddress;
    /**
     * 证婚人姓名
     */
    private String witnessUsername;
    /**
     * 证婚人userid
     */
    private Long witnessUserId;
    /**
     * 证婚人signuserid
     */
    private String witnessSignUserId;
    /**
     * 证婚人公钥地址
     */
    private String witnessPublicAddress;
    /**
     * 证书状态
     */
    private String certStatus;
    /**
     * 插入时间
     */
    private Date insertTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    private String marriageDesc;


    public String getMarriageDesc() {
        return marriageDesc;
    }

    public void setMarriageDesc(String marriageDesc) {
        this.marriageDesc = marriageDesc;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getMaleUsername() {
        return maleUsername;
    }

    public void setMaleUsername(String maleUsername) {
        this.maleUsername = maleUsername;
    }

    public Long getMaleUserId() {
        return maleUserId;
    }

    public void setMaleUserId(Long maleUserId) {
        this.maleUserId = maleUserId;
    }

    public String getMaleSignUserId() {
        return maleSignUserId;
    }

    public void setMaleSignUserId(String maleSignUserId) {
        this.maleSignUserId = maleSignUserId;
    }

    public String getMalePublicAddress() {
        return malePublicAddress;
    }

    public void setMalePublicAddress(String malePublicAddress) {
        this.malePublicAddress = malePublicAddress;
    }

    public String getFemaleUsername() {
        return femaleUsername;
    }

    public void setFemaleUsername(String femaleUsername) {
        this.femaleUsername = femaleUsername;
    }

    public Long getFemaleUserId() {
        return femaleUserId;
    }

    public void setFemaleUserId(Long femaleUserId) {
        this.femaleUserId = femaleUserId;
    }

    public String getFemaleSignUserId() {
        return femaleSignUserId;
    }

    public void setFemaleSignUserId(String femaleSignUserId) {
        this.femaleSignUserId = femaleSignUserId;
    }

    public String getFemalePublicAddress() {
        return femalePublicAddress;
    }

    public void setFemalePublicAddress(String femalePublicAddress) {
        this.femalePublicAddress = femalePublicAddress;
    }

    public String getWitnessUsername() {
        return witnessUsername;
    }

    public void setWitnessUsername(String witnessUsername) {
        this.witnessUsername = witnessUsername;
    }

    public Long getWitnessUserId() {
        return witnessUserId;
    }

    public void setWitnessUserId(Long witnessUserId) {
        this.witnessUserId = witnessUserId;
    }

    public String getWitnessSignUserId() {
        return witnessSignUserId;
    }

    public void setWitnessSignUserId(String witnessSignUserId) {
        this.witnessSignUserId = witnessSignUserId;
    }

    public String getWitnessPublicAddress() {
        return witnessPublicAddress;
    }

    public void setWitnessPublicAddress(String witnessPublicAddress) {
        this.witnessPublicAddress = witnessPublicAddress;
    }

    public String getCertStatus() {
        return certStatus;
    }

    public void setCertStatus(String certStatus) {
        this.certStatus = certStatus;
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
