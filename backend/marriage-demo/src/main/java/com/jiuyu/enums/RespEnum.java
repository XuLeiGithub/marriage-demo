package com.jiuyu.enums;

/**
 * <p>Title: RespEnum</p>
 * <p>Description: </p>
 * @author he_jiebing@jiuyv.com
   @date   2020年8月24日 下午3:56:12
 */

public enum RespEnum {
	
	SUCCESS(0,"success"),
	LOGIN_FAIL(10000,"用户名或密码不正确");
	
    /********************************* 结束**************************************/
	
    private int code;
    private String msg;
    RespEnum(int code,String msg){
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
