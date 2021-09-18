package com.jiuyu.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiuyu.entity.MarriageEvidenceEntity;

/**
 * 结婚存证表
 *
 * @author hejiebing
 * @email he_jiebing@jiuyv.com
 * @date 2021-08-19 21:07:24
 */
@Mapper
public interface MarriageEvidenceDao extends BaseMapper<MarriageEvidenceEntity> {

    MarriageEvidenceEntity queryByUserPublicAddress(@Param("publicAddress") String publicAddress);
}
