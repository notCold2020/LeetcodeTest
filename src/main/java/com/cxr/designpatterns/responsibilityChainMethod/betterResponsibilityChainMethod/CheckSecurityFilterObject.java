package com.cxr.designpatterns.responsibilityChainMethod.betterResponsibilityChainMethod;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 安全校验对象
 */
@Component
@Order(2) //校验顺序排第2
public class CheckSecurityFilterObject extends AbstractHandler {

    @Override
    public void doFilter(List<String> request, List<String> response) {
        //invoke Security check
        System.out.println("安全调用校验");
        response.add("安全调用校验");
    }
}
