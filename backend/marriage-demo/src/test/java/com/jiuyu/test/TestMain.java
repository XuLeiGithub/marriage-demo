package com.jiuyu.test;

import cn.hutool.json.JSONUtil;

import com.jiuyu.vo.ReqAddUser;

/**
 * <p>Title: TestMain</p>
 * <p>Description: </p>
 * @author he_jiebing@jiuyv.com
   @date   2021年8月20日 下午12:33:59
 */

public class TestMain {
	
	public static void main(String[] args) {
		testMale();
		//testFemale();
	}
	
	public static void testMale(){
		ReqAddUser req = new ReqAddUser();
		
		req.setAdminUserId(1L);
		req.setAge(28);
		req.setIdCard("340827199309879876");
		req.setFamilyAddress("上海市松江区泽悦路212弄");
		req.setSex("male");
		req.setUsername("Bob0820_4");
		System.out.println(JSONUtil.toJsonPrettyStr(req));
	}
	
	public static void testFemale(){
		ReqAddUser req = new ReqAddUser();
		
		req.setAdminUserId(1L);
		req.setAge(27);
		req.setIdCard("340827199309871230");
		req.setFamilyAddress("上海市徐汇区虹漕路421号");
		req.setSex("female");
		req.setUsername("Alice0820");
		System.out.println(JSONUtil.toJsonPrettyStr(req));
	}

}
