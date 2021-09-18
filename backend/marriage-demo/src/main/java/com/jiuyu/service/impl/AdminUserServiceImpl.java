package com.jiuyu.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiuyu.dao.AdminUserDao;
import com.jiuyu.entity.AdminUserEntity;
import com.jiuyu.service.AdminUserService;
import com.jiuyu.vo.ReqLoginVo;

@Service("adminUserService")
public class AdminUserServiceImpl extends ServiceImpl<AdminUserDao, AdminUserEntity> implements AdminUserService {

    @Autowired
    private AdminUserDao adminUserDao;

    @Override
    public AdminUserEntity login(ReqLoginVo req) {

        // TODO 后续密码需要加密处理
        String username = req.getUsername();
        String password = req.getPassword();

        AdminUserEntity result = adminUserDao.selectAdminUser(username, password);

        return result;
    }

}