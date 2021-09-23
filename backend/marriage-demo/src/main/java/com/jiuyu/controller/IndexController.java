package com.jiuyu.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jiuyu.common.R;
import com.jiuyu.entity.MarriageInfoEntity;
import com.jiuyu.service.MarriageInfoService;
import com.jiuyu.vo.ReqNull;
import com.jiuyu.vo.ResIndexVo;

/**
 * <p>Title: IndexController</p>
 * <p>Description: </p>
 *
 * @author he_jiebing@jiuyv.com
 * @date 2021年8月19日 下午9:56:18
 */
@RequestMapping("/marriage")
@RestController
public class IndexController {

    @Autowired
    private MarriageInfoService marriageInfoService;

    @GetMapping("/unlogin/index")
    public R queryUnLoginIndexInfo(@RequestParam(required = false, name = "name") ReqNull req) {
        List<MarriageInfoEntity> list = marriageInfoService.queryConfirmMarriageList();
        List<ResIndexVo> result = list.stream().map(item -> {
            ResIndexVo res = new ResIndexVo();
            BeanUtils.copyProperties(item, res);
            return res;
        }).collect(Collectors.toList());

        return R.ok(result);
    }

    @GetMapping("/login/index")
    public R queryLoginIndexInfo(@RequestParam(required = false, name = "name") ReqNull req) {
        List<MarriageInfoEntity> list = marriageInfoService.list();
        return R.ok(list);
    }

}
