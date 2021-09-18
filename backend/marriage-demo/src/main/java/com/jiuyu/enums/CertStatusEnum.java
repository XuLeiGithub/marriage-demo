package com.jiuyu.enums;

/**
 * <p>Title: RespEnum</p>
 * <p>Description: </p>
 * @author he_jiebing@jiuyv.com
   @date   2020年8月24日 下午3:56:12
 */

public enum CertStatusEnum {
	
	CONFIRMED(0,"confirmed"),
	DRAFT(1,"draft");
	
	
    private int code;
    private String msg;
    CertStatusEnum(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
