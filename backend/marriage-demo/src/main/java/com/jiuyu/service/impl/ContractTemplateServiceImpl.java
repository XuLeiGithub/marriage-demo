package com.jiuyu.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiuyu.common.Query;
import com.jiuyu.dao.ContractTemplateDao;
import com.jiuyu.entity.ContractTemplateEntity;
import com.jiuyu.service.ContractTemplateService;
import com.jiuyu.utils.PageUtils;


@Service("contractTemplateService")
public class ContractTemplateServiceImpl extends ServiceImpl<ContractTemplateDao, ContractTemplateEntity> implements ContractTemplateService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ContractTemplateEntity> page = this.page(
                new Query<ContractTemplateEntity>().getPage(params),
                new QueryWrapper<ContractTemplateEntity>()
        );

        return new PageUtils(page);
    }

}