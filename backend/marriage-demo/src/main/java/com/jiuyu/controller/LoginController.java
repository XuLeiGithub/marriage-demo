package com.jiuyu.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jiuyu.common.R;
import com.jiuyu.entity.AdminUserEntity;
import com.jiuyu.enums.RespEnum;
import com.jiuyu.service.AdminUserService;
import com.jiuyu.vo.ReqLoginVo;
import com.jiuyu.vo.ResLoginVo;

/**
 * <p>Title: LoginController</p>
 * <p>Description: </p>
 * @author he_jiebing@jiuyv.com
   @date   2021年8月19日 下午10:04:40
 */
@RequestMapping("/marriage")
@RestController
public class LoginController {
	
	@Autowired
	private AdminUserService adminUserService;
	
	@PostMapping("/login")
	public R login(@RequestBody ReqLoginVo req){
		AdminUserEntity result = adminUserService.login(req);
		if(null == result){
			return R.error(RespEnum.LOGIN_FAIL);
		}
		ResLoginVo res = new ResLoginVo();
		res.setAdminUserId(result.getId());
		res.setUsername(result.getUsername());
		return R.ok(result);
	}

}
