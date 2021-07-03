package com.cxr.designpatterns.responsibilityChainMethod;

import java.util.HashSet;

public class FirstInterview extends Handler {

    @Override
    public void handleRequest(Integer times) {
        if (times == 1) {
            System.out.println("第一次面试");
        } else {
            //传递给责任链的下一位，我处理不了，我把这个请求原封不动（times）的给你
            handler.handleRequest(times);
        }
    }
}
