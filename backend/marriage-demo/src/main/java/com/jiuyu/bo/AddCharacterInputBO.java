package com.jiuyu.bo;

import java.io.Serializable;

/**
 * @author chengliang
 * @date 2021/08/14 10:25
 */
public class AddCharacterInputBO implements Serializable {
    private static final long serialVersionUID = -3406312637454713106L;

    private String amount;

    private String summary;

    public AddCharacterInputBO() {
    }

    public AddCharacterInputBO(String amount, String summary) {
        this.amount = amount;
        this.summary = summary;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

   
}
