package com.jiuyu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiuyu.common.Query;
import com.jiuyu.dao.MarriageInfoDao;
import com.jiuyu.entity.MarriageInfoEntity;
import com.jiuyu.enums.CertStatusEnum;
import com.jiuyu.service.MarriageInfoService;
import com.jiuyu.utils.PageUtils;


@Service("marriageInfoService")
public class MarriageInfoServiceImpl extends ServiceImpl<MarriageInfoDao, MarriageInfoEntity> implements MarriageInfoService {

    @Autowired
    private MarriageInfoDao marriageInfoDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MarriageInfoEntity> page = this.page(
                new Query<MarriageInfoEntity>().getPage(params),
                new QueryWrapper<MarriageInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<MarriageInfoEntity> queryConfirmMarriageList() {
        List<MarriageInfoEntity> list = marriageInfoDao.selectList(new QueryWrapper<MarriageInfoEntity>().eq("cert_status", CertStatusEnum.CONFIRMED.getMsg()));
        return list;
    }

}