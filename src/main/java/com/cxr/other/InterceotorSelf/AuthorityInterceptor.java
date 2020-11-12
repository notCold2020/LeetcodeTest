package com.cxr.other.InterceotorSelf;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthorityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //这个提供了关于被拦截方法的一切
        Method method = handlerMethod.getMethod();
        Authority annotation = method.getAnnotation(Authority.class);
        if (annotation == null) {
            //如果没注解直接放行
            return false;
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


}
