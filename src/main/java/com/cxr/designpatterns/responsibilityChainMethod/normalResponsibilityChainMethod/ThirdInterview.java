package com.cxr.designpatterns.responsibilityChainMethod.normalResponsibilityChainMethod;

public class ThirdInterview extends Handler {
    @Override
    public void handleRequest(Integer times) {
        if (times == 3) {
            System.out.println("第三次面试");
        } else {
            handler.handleRequest(times);
        }
    }
}
