package com.jiuyu.vo;

import java.io.Serializable;

/**
 * <p>Title: ReqSignVo</p>
 * <p>Description: </p>
 *
 * @author he_jiebing@jiuyv.com
 * @date 2021年8月20日 上午11:09:12
 */

public class ReqSignVo implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private Long adminUserId;

    private Long userId;

    private String certificateNumber;

    public Long getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Long adminUserId) {
        this.adminUserId = adminUserId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }
}
