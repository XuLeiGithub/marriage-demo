package com.jiuyu.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiuyu.entity.MarriageInfoEntity;
import com.jiuyu.utils.PageUtils;

/**
 * 结婚证信息表
 *
 * @author hejiebing
 * @email he_jiebing@jiuyv.com
 * @date 2021-08-19 21:07:24
 */
public interface MarriageInfoService extends IService<MarriageInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询首页已成功的新人信息
     *
     * @return 已登记结婚的新人信息
     */
    List<MarriageInfoEntity> queryConfirmMarriageList();
}

