package com.cxr.other.point;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.*;
import org.springframework.stereotype.Component;


@Aspect
@Component
 public class AspectSelf {

    Logger logger = LoggerFactory.getLogger(Aspect.class);
    /**
     * 前置通知
     */

    @Pointcut("execution(* com.cxr.other.point.AspectTest.beforeTest(..))")
    private void beforePointcut(){
        logger.info("我是中间的方法，我不知道自己是干啥的，我也不会被输出。。。");
    }

    @Before("beforePointcut()")
    public void before(JoinPoint joinpoint){
        System.out.println("-----");
        logger.info("我是前置通知");
        logger.info(joinpoint.getSignature().toString());
    }

}
