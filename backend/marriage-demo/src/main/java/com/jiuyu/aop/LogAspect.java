package com.jiuyu.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.hutool.core.util.ReUtil;
import cn.hutool.json.JSONUtil;

import com.jiuyu.common.R;
import com.jiuyu.common.RegexConstant;


/**
 * @Title: LogAspect
 * @Description: TODO
 * @author YuWenJing
 * @date 2019-03-27 17:06
 * 
 */
@Aspect
@Component
public class LogAspect {
	
    @Pointcut("(execution(public * com.jiuyu.controller.*Controller.*(..)))")
     public void controllerReqResAspect() {    
         //切点
     }
    
    //不能使用before,会出现数据无法脱敏
    @Around(value = "controllerReqResAspect()")
    public Object printReq(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        LoggerFactory.getLogger(proceedingJoinPoint.getTarget().getClass()).info("method:{},req:{}",proceedingJoinPoint.getSignature().getName(),JSONUtil.toJsonStr(proceedingJoinPoint.getArgs()[0]));
        Object returnValue = proceedingJoinPoint.proceed();  //执行连接点方法，object：方法返回值
        return returnValue;
    }

    @AfterReturning(value = "controllerReqResAspect()",returning="res")
    public void printRes(JoinPoint joinPoint,Object res) {
        // 过滤指定接口，不打印消息体
        boolean isMatch = false;
        for (String regex : RegexConstant.LOG_FILTER_METHOD_REGEX.split(",")) {
            if (ReUtil.isMatch(regex, joinPoint.getSignature().toShortString())) {
                isMatch = true;
                break;
            }
        }
        if (isMatch) {
            String msg = "success";
            if (res instanceof R && ((R) res).containsKey("msg")) {
                msg = (String) ((R) res).get("msg");
            }
            LoggerFactory.getLogger(joinPoint.getTarget().getClass()).info("method:{},res:{}",joinPoint.getSignature().getName(),msg);
        } else {
            LoggerFactory.getLogger(joinPoint.getTarget().getClass()).info("method:{},res:{}",joinPoint.getSignature().getName(),JSONUtil.toJsonStr(res));
        }
    }
    
    @AfterThrowing(pointcut = "controllerReqResAspect()", throwing = "e")//切点在webpointCut()
    public void handleThrowing(JoinPoint joinPoint, Exception e) {//controller类抛出的异常在这边捕获
        LoggerFactory.getLogger(joinPoint.getTarget().getClass()).info("method:{},res:{}",
                joinPoint.getSignature().getName(),JSONUtil.toJsonStr(joinPoint.getArgs()[0]));
    }

}
