package com.jiuyu.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Title: ResMarriageDeatailVo</p>
 * <p>Description: </p>
 *
 * @author he_jiebing@jiuyv.com
 * @date 2021年8月20日 下午3:54:16
 */

public class ResMarriageDeatailVo implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7775857195217837728L;

    private String maleUsername;

    private String maleSignStatus;

    private String femaleUsername;

    private String femaleSignStatus;

    private String witnessUsername;

    private String witnessSignStatus;

    private String certStatus;

    private String marriageDesc;

    private String certificateNumber;

    private Date updateTime;


    public String getMaleSignStatus() {
        return maleSignStatus;
    }

    public void setMaleSignStatus(String maleSignStatus) {
        this.maleSignStatus = maleSignStatus;
    }

    public String getFemaleSignStatus() {
        return femaleSignStatus;
    }

    public void setFemaleSignStatus(String femaleSignStatus) {
        this.femaleSignStatus = femaleSignStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

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

    public String getCertStatus() {
        return certStatus;
    }

    public void setCertStatus(String certStatus) {
        this.certStatus = certStatus;
    }

    public String getMarriageDesc() {
        return marriageDesc;
    }

    public void setMarriageDesc(String marriageDesc) {
        this.marriageDesc = marriageDesc;
    }

    public String getWitnessUsername() {
        return witnessUsername;
    }

    public void setWitnessUsername(String witnessUsername) {
        this.witnessUsername = witnessUsername;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getWitnessSignStatus() {
        return witnessSignStatus;
    }

    public void setWitnessSignStatus(String witnessSignStatus) {
        this.witnessSignStatus = witnessSignStatus;
    }
}
