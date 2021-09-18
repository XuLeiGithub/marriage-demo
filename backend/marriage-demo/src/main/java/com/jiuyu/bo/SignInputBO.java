package com.jiuyu.bo;

import java.io.Serializable;

/**
 * @author chengliang
 * @date 2021/08/25 16:41
 */
public class SignInputBO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String certificateNumber;

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }
}
