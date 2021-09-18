/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.jiuyu.common;

import java.util.HashMap;

import org.apache.http.HttpStatus;

import com.jiuyu.enums.RespEnum;

/**
 * 返回数据
 *
 * @author Mark sunlightcs@gmail.com
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public R() {
		put("code", RespEnum.SUCCESS.getCode());
		put("msg", RespEnum.SUCCESS.getMsg());
	}
	
	public static R error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}
	
	public static R error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}
	
	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}
	
	public static R error(RespEnum respEnum) {
		R r = new R();
		r.put("code", respEnum.getCode());
		r.put("msg", respEnum.getMsg());
		return r;
	}
	
	public static R ok() {
		return new R();
	}

	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
	
	public static R ok(Object obj){
		R r = new R();
		r.put("code", RespEnum.SUCCESS.getCode());
		r.put("msg", RespEnum.SUCCESS.getMsg());
		r.put("data",obj);
		return r;
	}
	
	public static Boolean isOk(R r) {
		Integer  code = r.get("code")==null?null:Integer.valueOf(r.get("code").toString());
		return code==RespEnum.SUCCESS.getCode();
	}
}
