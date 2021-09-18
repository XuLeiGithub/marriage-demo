package com.jiuyu.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jiuyu.common.R;
import com.jiuyu.entity.UserInfoEntity;
import com.jiuyu.enums.MarriageStatusEnum;
import com.jiuyu.service.UserInfoService;
import com.jiuyu.vo.ReqAddUser;
import com.jiuyu.vo.ReqNull;
import com.jiuyu.vo.ResAddUser;

/**
 * <p>
 * Title: UserController
 * </p>
 * <p>
 * Description:
 * </p>
 *
 * @author he_jiebing@jiuyv.com
 * @date 2021年8月19日 下午9:56:29
 */
@RequestMapping("/marriage")
@RestController
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/addUser")
    public R addUser(@RequestBody ReqAddUser req) {
        ResAddUser result = userInfoService.addUser(req);
        return R.ok(result);
    }

    @GetMapping("/user/list")
    public R queryUserList(@RequestParam(required = false, name = "name") ReqNull req) {
        List<UserInfoEntity> list = userInfoService.list();
        return R.ok(list);
    }

    @GetMapping("/user/list/{sex}")
    public R queryUserBySex(@PathVariable("sex") String sex) {
        List<UserInfoEntity> list = userInfoService.list();
        List<UserInfoEntity> result = list.stream()
                .filter(item -> item.getSex().equals(sex)
                        && item.getMarriageStatus().equals(
                        MarriageStatusEnum.NO_MARRIAGE.getCode()))
                .collect(Collectors.toList());
        return R.ok(result);
    }

}
