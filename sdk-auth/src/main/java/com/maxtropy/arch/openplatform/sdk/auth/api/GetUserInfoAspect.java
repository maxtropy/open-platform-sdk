package com.maxtropy.arch.openplatform.sdk.auth.api;

import com.maxtropy.arch.openplatform.sdk.core.auth.GetUserInfo;
import com.maxtropy.arch.openplatform.sdk.core.auth.RequestContextUtil;
import com.maxtropy.arch.openplatform.sdk.core.auth.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
public class GetUserInfoAspect {

    @Pointcut("execution(* com.maxtropy..controller.*.*(..))")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        log.info("进入获取用户信息的切面...");
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Annotation[][] annotations = method.getParameterAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            Object arg = args[i];
            Annotation[] annotation = annotations[i];
            if(arg == null || annotation.length == 0) {
                continue;
            }
            for (Annotation anno : annotation) {
                log.info("匹配方法参数注解...");
                if(anno.annotationType().equals(GetUserInfo.class)) {
                    log.info("匹配到GetUserInfo注解...");
                    UserInfo userInfo = RequestContextUtil.getUserInfo();
                    log.info("构造用户信息...");
                    UserInfo param = (UserInfo) arg;
                    param.setSessionId(userInfo.getSessionId());
                    param.setCurrentStaffId(userInfo.getCurrentStaffId());
                    param.setTenantUuid(userInfo.getTenantUuid());
                    param.setUserId(userInfo.getUserId());
                    param.setName(userInfo.getName());
                    param.setHeadPic(userInfo.getHeadPic());
                    log.info("用户信息: {}",param);
                    break;
                }
            }
        }
    }
}
