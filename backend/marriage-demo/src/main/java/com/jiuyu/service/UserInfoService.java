package com.jiuyu.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiuyu.entity.UserInfoEntity;
import com.jiuyu.utils.PageUtils;
import com.jiuyu.vo.ReqAddUser;
import com.jiuyu.vo.ResAddUser;

/**
 * 用户信息表
 *
 * @author hejiebing
 * @email he_jiebing@jiuyv.com
 * @date 2021-08-19 21:07:24
 */
public interface UserInfoService extends IService<UserInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 登记信息
     *
     * @param req 用户信息
     * @return
     */
    ResAddUser addUser(ReqAddUser req);

}

