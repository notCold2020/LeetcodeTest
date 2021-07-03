package com.cxr.designpatterns.observerMethod;

public class FirstObserver implements Observer{
    @Override
    public void update(String message) {
        System.out.println("我是一号观察者，我收到了事件");
    }
}

