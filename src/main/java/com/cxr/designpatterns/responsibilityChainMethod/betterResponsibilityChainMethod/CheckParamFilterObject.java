package com.cxr.designpatterns.responsibilityChainMethod.betterResponsibilityChainMethod;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 参数校验对象
 **/
@Component
@Order(1) //顺序排第1，最先校验
public class CheckParamFilterObject extends AbstractHandler {

    @Override
    public void doFilter(List<String> request, List<String> response) {
        System.out.println("非空参数检查");
        response.add("非空参数检查");


    }
}
