package com.jiuyu.bo;


import java.io.Serializable;


/**
 * @author chengliang
 * @date 2021/08/24 22:35
 */
public class DeployEviInputBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 证书id
     */
    private String certificateNumber;
    /**
     * 证婚人的公钥地址
     */
    private String witnessAddress;
    /**
     * 证婚人姓名
     */
    private String  witnessName;
    /**
     * 男方公钥地址
     */
    private String maleAddress;
    /**
     * 男方信息
     */
    private String maleSummary;
    /**
     * 女方公钥地址
     */
    private String femaleAddress;
    /**
     * 女方信息
     */
    private String femaleSummary;

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getWitnessAddress() {
        return witnessAddress;
    }

    public void setWitnessAddress(String witnessAddress) {
        this.witnessAddress = witnessAddress;
    }

	public String getWitnessName() {
		return witnessName;
	}

	public void setWitnessName(String witnessName) {
		this.witnessName = witnessName;
	}

	public String getMaleAddress() {
        return maleAddress;
    }

    public void setMaleAddress(String maleAddress) {
        this.maleAddress = maleAddress;
    }

    public String getMaleSummary() {
        return maleSummary;
    }

    public void setMaleSummary(String maleSummary) {
        this.maleSummary = maleSummary;
    }

    public String getFemaleAddress() {
        return femaleAddress;
    }

    public void setFemaleAddress(String femaleAddress) {
        this.femaleAddress = femaleAddress;
    }

    public String getFemaleSummary() {
        return femaleSummary;
    }

    public void setFemaleSummary(String femaleSummary) {
        this.femaleSummary = femaleSummary;
    }
}
