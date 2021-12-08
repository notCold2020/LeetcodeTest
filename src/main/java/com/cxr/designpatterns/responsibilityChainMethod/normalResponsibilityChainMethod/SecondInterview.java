package com.cxr.designpatterns.responsibilityChainMethod.normalResponsibilityChainMethod;

public class SecondInterview extends Handler {
    @Override
    public void handleRequest(Integer times) {
        if (times == 2) {
            System.out.println("第二次面试");
        } else {
            /**
             * 父类的那个handler
             */
            handler.handleRequest(times);
        }
    }
}
