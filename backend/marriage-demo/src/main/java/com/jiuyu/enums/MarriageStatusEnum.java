package com.jiuyu.enums;

/**
 * <p>Title: RespEnum</p>
 * <p>Description: </p>
 * @author he_jiebing@jiuyv.com
   @date   2020年8月24日 下午3:56:12
 */

public enum MarriageStatusEnum {
	
	HAS_MARRIAHE("00","HAS_MARRIAHE"),
	NO_MARRIAGE("01","NO_MARRIAGE");
	
	
    private String code;
    private String msg;
    MarriageStatusEnum(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
