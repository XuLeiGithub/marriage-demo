package com.jiuyu.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiuyu.entity.ContractTemplateEntity;
import com.jiuyu.utils.PageUtils;

/**
 * 合约模板表
 *
 * @author hejiebing
 * @email he_jiebing@jiuyv.com
 * @date 2021-08-19 21:07:24
 */
public interface ContractTemplateService extends IService<ContractTemplateEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

