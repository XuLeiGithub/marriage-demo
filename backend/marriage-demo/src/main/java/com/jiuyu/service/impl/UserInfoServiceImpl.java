package com.jiuyu.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiuyu.bo.ContractDeployReqBO;
import com.jiuyu.common.GlobalConstant;
import com.jiuyu.common.Query;
import com.jiuyu.dao.AdminUserDao;
import com.jiuyu.dao.ContractTemplateDao;
import com.jiuyu.dao.UserInfoDao;
import com.jiuyu.entity.ContractTemplateEntity;
import com.jiuyu.entity.UserInfoEntity;
import com.jiuyu.enums.MarriageStatusEnum;
import com.jiuyu.service.UserInfoService;
import com.jiuyu.utils.DateUtils;
import com.jiuyu.utils.PageUtils;
import com.jiuyu.vo.ReqAddUser;
import com.jiuyu.vo.ResAddUser;
import com.webank.webase.app.sdk.client.AppClient;
import com.webank.webase.app.sdk.config.HttpConfig;
import com.webank.webase.app.sdk.dto.req.ReqContractAddressSave;
import com.webank.webase.app.sdk.dto.req.ReqContractSourceSave;
import com.webank.webase.app.sdk.dto.req.ReqContractSourceSave.ContractSource;
import com.webank.webase.app.sdk.dto.req.ReqNewUser;
import com.webank.webase.app.sdk.dto.rsp.RspUserInfo;


@Service("userInfoService")
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfoEntity> implements UserInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Value("${webase.node.mgr.url}")
    private String url;

    @Value("${webase.node.mgr.appKey}")
    private String appKey;

    @Value("${webase.node.mgr.appSecret}")
    private String appSecret;

    @Value("${webase.node.mgr.isTransferEncrypt}")
    private Boolean isTransferEncrypt;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private ContractTemplateDao contractTemplateDao;

    private static AppClient appClient = null;

    private static final String ACCOUNT = "admin";

    private static final String CONTRACT_VERSION = "1.0.0";


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserInfoEntity> page = this.page(
                new Query<UserInfoEntity>().getPage(params),
                new QueryWrapper<UserInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResAddUser addUser(ReqAddUser req) {
        // 1.先落地tbl_user_info 表
        String username = req.getUsername();
        Date date = Date.from(Instant.now());
        UserInfoEntity userEntity = new UserInfoEntity();
        BeanUtils.copyProperties(req, userEntity);
        userEntity.setInsertTime(date);
        userEntity.setUpdateTime(date);

        // 2.调用WeBASE-APP-SDK 获取用户公钥地址等信息
        ReqNewUser reqNewUser = new ReqNewUser();
        reqNewUser.setGroupId(1);
        reqNewUser.setUserName(userEntity.getUsername());
        reqNewUser.setAccount("admin");
        reqNewUser.setDescription(GlobalConstant.APP_PREIX + username);
        HttpConfig httpConfig = new HttpConfig(30, 30, 30);
        appClient = new AppClient(url, appKey, appSecret, isTransferEncrypt, httpConfig);
        RspUserInfo resp = appClient.newUser(reqNewUser);
        String address = resp.getAddress();
        String signUserId = resp.getSignUserId();
        userEntity.setSignUserId(signUserId);
        userEntity.setPublicAddress(address);

        userEntity.setMarriageStatus(MarriageStatusEnum.NO_MARRIAGE.getCode());
        userInfoDao.insert(userEntity);
        ResAddUser res = new ResAddUser();
        res.setTxHash("添加成功");
        return res;
    }


    private void callWebaseSdkContractAddressSave(AppClient appClient, ContractDeployReqBO contractDeployReqBO, String contractAddr, String username) {
        ReqContractAddressSave reqContractAddressSave = new ReqContractAddressSave();
        reqContractAddressSave.setGroupId(1);
        reqContractAddressSave.setContractName("MarriageEvidence");
        reqContractAddressSave.setContractPath(GlobalConstant.APP_PREIX + username + "_" + DateUtils.dateTimeNow());
        reqContractAddressSave.setContractVersion(CONTRACT_VERSION);
        reqContractAddressSave.setContractAddress(contractAddr);
        LOGGER.info("调用WebaseSdk AddressSave接口,请求参数:>>{}", JSONUtil.toJsonStr(reqContractAddressSave));
        appClient.contractAddressSave(reqContractAddressSave);
    }

    private AppClient getAppClient() {
        HttpConfig httpConfig = new HttpConfig(30, 30, 30);
        return new AppClient(url, appKey, appSecret, isTransferEncrypt, httpConfig);
    }

}