package com.jiuyu.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jiuyu.common.R;
import com.jiuyu.entity.UserInfoEntity;
import com.jiuyu.enums.MarriageStatusEnum;
import com.jiuyu.service.UserInfoService;
import com.jiuyu.vo.ReqAddUser;
import com.jiuyu.vo.ReqNull;
import com.jiuyu.vo.ResAddUser;
import com.webank.webase.app.sdk.client.AppClient;
import com.webank.webase.app.sdk.config.HttpConfig;
import com.webank.webase.app.sdk.dto.rsp.RspUserInfo;

/**
 * <p>
 * Title: DemoController
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author he_jiebing@jiuyv.com
 * @date 2021年9月27日 下午2:01:56
 */
@RestController
public class DemoController {

	@Value("${webase.node.mgr.url}")
	private String url;

	@Value("${webase.node.mgr.appKey}")
	private String appKey;

	@Value("${webase.node.mgr.appSecret}")
	private String appSecret;

	@Value("${webase.node.mgr.isTransferEncrypt}")
	private Boolean isTransferEncrypt;
	
    private static AppClient appClient = null;

    private static final String ACCOUNT = "admin";

	@GetMapping("/userInfo/{userId}")
	public RspUserInfo getUserInfo(@PathVariable("userId") Integer userId) {
		// 2.调用WeBASE-APP-SDK 获取用户公钥地址等信息
		HttpConfig httpConfig = new HttpConfig(30, 30, 30);
		appClient = new AppClient(url, appKey, appSecret, isTransferEncrypt,httpConfig);
		RspUserInfo userInfo = appClient.userInfo(userId);
		return userInfo;
	}

}
