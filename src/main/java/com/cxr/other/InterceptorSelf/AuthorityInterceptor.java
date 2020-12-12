package com.cxr.other.InterceptorSelf;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 执行顺序:
 * 项目一启动：
 * Filter:init(只执行一次)
 *
 * Filter:doFilter
 * preHandle
 * controller
 * postHandle
 * afterCompletion
 *
 * Filter:distory
 * 关闭server中的按钮才调用,如tomcat中的shutdown
 */
public class AuthorityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //这个提供了关于被拦截方法的一切
        Method method = handlerMethod.getMethod();
        Authority annotation = method.getAnnotation(Authority.class);
        if (annotation == null) {
            //如果没注解直接放行
            return true;
        }
        //get/post都能获取到，只要是前端传过来的都可以 request也就是请求参数呗
//        String userAuthority = request.getParameter("userAuthority");
        String userAuthority =  String.valueOf(request.getAttribute("userAuthority"));
        if (userAuthority.equals("admin") && annotation.value().equals("放行")) {
            return true;
        }

        System.out.println("------------无法通过--------------");
        return false;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        /**
         * 看入参有ModelAndView 对视图进行渲染
         */
        System.out.println("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        /**
         * 请求处理完毕回调方法
         */
        System.out.println("afterCompletion");
    }
}
