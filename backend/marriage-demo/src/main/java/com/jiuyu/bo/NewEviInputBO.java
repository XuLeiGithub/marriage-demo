package com.jiuyu.bo;

import java.io.Serializable;

/**
 * @author chengliang
 * @date 2021/08/24 17:58
 */
public class NewEviInputBO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 证书id
     */
    private String certificateNumber;
    /**
     * 信息
     */
    private String evi;

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getEvi() {
        return evi;
    }

    public void setEvi(String evi) {
        this.evi = evi;
    }
}
