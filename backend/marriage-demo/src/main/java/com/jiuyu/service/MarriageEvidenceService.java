package com.jiuyu.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiuyu.common.R;
import com.jiuyu.entity.MarriageEvidenceEntity;
import com.jiuyu.utils.PageUtils;
import com.jiuyu.vo.ReqCreateMarriageEvidenceVo;
import com.jiuyu.vo.ReqSignVo;
import com.jiuyu.vo.ResCreateMarriageEvidenceVo;
import com.jiuyu.vo.ResMarriageDeatailVo;

/**
 * 结婚存证表
 *
 * @author hejiebing
 * @email he_jiebing@jiuyv.com
 * @date 2021-08-19 21:07:24
 */
public interface MarriageEvidenceService extends IService<MarriageEvidenceEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 创建结婚证书
     *
     * @param req 管理员id、男方id、女方id
     * @return
     */
    ResCreateMarriageEvidenceVo createMarriageEvidence(ReqCreateMarriageEvidenceVo req);

    /**
     * 签名
     *
     * @param req 管理员id、签名方id
     * @return
     */
    R sign(ReqSignVo req);

    /**
     * 查询详情
     *
     * @param id 证书编号
     * @return
     */
    ResMarriageDeatailVo queryMarriageDetail(String id);
}

