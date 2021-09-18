package com.jiuyu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 结婚存证表
 *
 * @author hejiebing
 * @email he_jiebing@jiuyv.com
 * @date 2021-08-19 21:07:24
 */
@TableName("tbl_marriage_evidence")
public class MarriageEvidenceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 存证key
     */
    private String evidenceKey;
    /**
     * 存证value
     */
    private String evidenceValue;
    /**
     * 需要签名各方(公钥地址，民政局管理员，男方，女方)
     */
    private String needSigners;
    /**
     * 已签名各方(公钥地址，民政局管理员，男方，女方)
     */
    private String hasSigners;
    /**
     * 交易id
     */
    private String txId;
    /**
     * 插入时间
     */
    private Date insertTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 证书编号
     */
    private String certificateNumber;

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvidenceKey() {
        return evidenceKey;
    }

    public void setEvidenceKey(String evidenceKey) {
        this.evidenceKey = evidenceKey;
    }

    public String getEvidenceValue() {
        return evidenceValue;
    }

    public void setEvidenceValue(String evidenceValue) {
        this.evidenceValue = evidenceValue;
    }

    public String getNeedSigners() {
        return needSigners;
    }

    public void setNeedSigners(String needSigners) {
        this.needSigners = needSigners;
    }

    public String getHasSigners() {
        return hasSigners;
    }

    public void setHasSigners(String hasSigners) {
        this.hasSigners = hasSigners;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
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
