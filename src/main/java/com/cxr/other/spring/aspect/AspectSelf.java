package com.cxr.other.spring.aspect;


import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.*;
import org.springframework.stereotype.Component;
import com.cxr.other.spring.InterfaceSelf.*;


@Aspect
@Component  //切面想要实现是肯定要交给spring来管理的，即@Compoment
public class AspectSelf {


    Logger logger = LoggerFactory.getLogger(Aspect.class);

    /**
     * 前置通知
     */

    @Pointcut("execution(* com.cxr.other.spring.aspect.AspectTest.beforeTest())")
    private void beforePointcut() {
        logger.info("我是中间的方法，我不知道自己是干啥的，我也不会被输出。。。");
    }

    @Before("beforePointcut()")
    public void before(JoinPoint joinpoint) {
        System.out.println("-----");
        logger.info("我是前置通知");
        logger.info(joinpoint.getSignature().toString());
    }

    /**
     * todo:
     * @before不能提前return方法，需要用拦截器
     * 但是@Around可以 ，只不过 会把切面的返回值强制转换为 方法 的 返回值
     * 就算 ！！！！我们在被切的 方法 中返回 了，也不行 依旧会执行切面，然后返回下面的555！！！
     */
    @Around("beforePointcut()")
    public String around(ProceedingJoinPoint joinpoint) throws Throwable {
        Object proceed = joinpoint.proceed();
        return "555";
    }


    @Pointcut("@annotation(com.cxr.other.spring.InterfaceSelf.InterfaceMySelf)")
    public void check() {

    }

    /**
     * 之前想对某个打了自定义注解的方法进行拦截，得扫描整个包。其实可以像下面这样写。
     * 而且切点表达式是一种规则，并不是只能指定包的路径，还可以配置比如：
     * 对所有修饰符为Public的方法进行限制，等
     * jianshu.com/p/37a5ee452edb
     */
    @Around("@annotation(interfaceMySelf)")//这个是形参
    public Object checkInterface(ProceedingJoinPoint joinPoint, InterfaceMySelf interfaceMySelf) throws Throwable {
        joinPoint.proceed();
        return "1";
    }


}
