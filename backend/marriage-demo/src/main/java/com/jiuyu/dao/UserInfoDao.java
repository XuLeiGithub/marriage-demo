package com.jiuyu.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiuyu.entity.UserInfoEntity;

/**
 * 用户信息表
 * 
 * @author hejiebing
 * @email he_jiebing@jiuyv.com
 * @date 2021-08-19 21:07:24
 */
@Mapper
public interface UserInfoDao extends BaseMapper<UserInfoEntity> {
	
}
