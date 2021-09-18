package com.jiuyu.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiuyu.entity.AdminUserEntity;

/**
 * 管理员信息表
 * 
 * @author hejiebing
 * @email he_jiebing@jiuyv.com
 * @date 2021-08-19 21:07:24
 */
@Mapper
public interface AdminUserDao extends BaseMapper<AdminUserEntity> {

	AdminUserEntity selectAdminUser(@Param("username") String username, @Param("password")String password);
	
}
