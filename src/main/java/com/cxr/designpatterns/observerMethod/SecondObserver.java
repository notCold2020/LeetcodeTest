package com.cxr.designpatterns.observerMethod;

public class SecondObserver implements Observer{
    @Override
    public void update(String message) {
        System.out.println("我是二号观察者，我收到了事件");
    }
}
