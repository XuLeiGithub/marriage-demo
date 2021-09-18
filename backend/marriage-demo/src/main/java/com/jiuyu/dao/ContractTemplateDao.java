package com.jiuyu.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiuyu.entity.ContractTemplateEntity;

/**
 * 合约模板表
 * 
 * @author hejiebing
 * @email he_jiebing@jiuyv.com
 * @date 2021-08-19 21:07:24
 */
@Mapper
public interface ContractTemplateDao extends BaseMapper<ContractTemplateEntity> {
	
	/**
	 * 查询模板信息
	 * @param template
	 * @return
	 */
	ContractTemplateEntity queryByTemplate(@Param("template") String template);
	
}
