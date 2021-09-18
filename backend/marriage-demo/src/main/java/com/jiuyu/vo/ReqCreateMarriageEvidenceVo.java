package com.jiuyu.vo;

import java.io.Serializable;

/**
 * <p>Title: ReqCreateMarriageEvidenceVo</p>
 * <p>Description: </p>
 *
 * @author he_jiebing@jiuyv.com
 * @date 2021年8月20日 上午9:51:42
 */

public class ReqCreateMarriageEvidenceVo implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1861595425924703910L;

    private Long adminUserId;

    private Long maleUserId;

    private Long femaleUserId;

    private Long witnessUserId;

    public Long getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Long adminUserId) {
        this.adminUserId = adminUserId;
    }

    public Long getMaleUserId() {
        return maleUserId;
    }

    public void setMaleUserId(Long maleUserId) {
        this.maleUserId = maleUserId;
    }

    public Long getFemaleUserId() {
        return femaleUserId;
    }

    public void setFemaleUserId(Long femaleUserId) {
        this.femaleUserId = femaleUserId;
    }

    public Long getWitnessUserId() {
        return witnessUserId;
    }

    public void setWitnessUserId(Long witnessUserId) {
        this.witnessUserId = witnessUserId;
    }
}
