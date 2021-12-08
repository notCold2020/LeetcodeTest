package com.cxr.designpatterns.responsibilityChainMethod.betterResponsibilityChainMethod;

import org.omg.CORBA.Request;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.xml.ws.Response;

/**
 *  黑名单校验对象
 */
@Component
@Order(3) //校验顺序排第3
public class CheckBlackFilterObject extends AbstractHandler {

    @Override
    public void doFilter(Request request, Response response) {
        //invoke black list check
        System.out.println("校验黑名单");
    }
}
