package com.cxr.designpatterns.responsibilityChainMethod;

import java.util.logging.Handler;

/**
 * 核心思想：
 * 请求来到了责任链A-B-C
 * A处理不了会把请求给B，B处理不了给C
 *
 * 1.框架里面哪里用到了？
 * Dubbo的拦截器，很多拦截器组成个责任链，只要有一个不通过就被拦截。
 * 而且容易若站
 */
public class Test {
    public static void main(String[] args) {
        FirstInterview first = new FirstInterview();
        SecondInterview second = new SecondInterview();
        /**
         * 可以理解为first设置自己的兜底是second
         * first把自己类里面的全局变量设置为second，这样调用handler.handleRequest(times);的时候调用的就是second的
         */
        first.setHandler(second);
        ThirdInterview third = new ThirdInterview();
        second.setHandler(third);//可以理解为second设置自己的兜底是third

        /**
         * first调用的，但是它处理不了 就给second了，second可以调用
         */
        first.handleRequest(2);
    }
}
