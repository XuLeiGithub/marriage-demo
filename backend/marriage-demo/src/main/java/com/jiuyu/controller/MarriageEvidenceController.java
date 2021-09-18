package com.jiuyu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jiuyu.common.R;
import com.jiuyu.service.MarriageEvidenceService;
import com.jiuyu.vo.ReqCreateMarriageEvidenceVo;
import com.jiuyu.vo.ReqSignVo;
import com.jiuyu.vo.ResCreateMarriageEvidenceVo;
import com.jiuyu.vo.ResMarriageDeatailVo;

/**
 * <p>Title: MarriageEvidenceController</p>
 * <p>Description: </p>
 *
 * @author he_jiebing@jiuyv.com
 * @date 2021年8月19日 下午11:27:12
 */
@RequestMapping("/marriage")
@RestController
public class MarriageEvidenceController {

    @Autowired
    private MarriageEvidenceService marriageEvidenceService;


    @PostMapping("/create")
    public R createMarriageEvidence(@RequestBody ReqCreateMarriageEvidenceVo req) {
        ResCreateMarriageEvidenceVo res = marriageEvidenceService.createMarriageEvidence(req);
        return R.ok(res);
    }

    @PostMapping("/sign")
    public R sign(@RequestBody ReqSignVo req) {
        return marriageEvidenceService.sign(req);
    }

    @GetMapping("/info/{id}")
    public R queryMarriageDetail(@PathVariable("id") String certificateNumber) {
        ResMarriageDeatailVo res = marriageEvidenceService.queryMarriageDetail(certificateNumber);
        return R.ok(res);
    }

}
