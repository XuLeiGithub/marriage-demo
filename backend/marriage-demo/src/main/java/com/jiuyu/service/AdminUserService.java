package com.jiuyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiuyu.entity.AdminUserEntity;
import com.jiuyu.vo.ReqLoginVo;

/**
 * 管理员信息表
 *
 * @author hejiebing
 * @email he_jiebing@jiuyv.com
 * @date 2021-08-19 21:07:24
 */
public interface AdminUserService extends IService<AdminUserEntity> {

    /**
     * 管理员登录
     *
     * @param req 管理员账号密码
     */
    AdminUserEntity login(ReqLoginVo req);

}

