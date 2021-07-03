package com.cxr.designpatterns.observerMethod;

/**
 * 主题接口：
 * 你要是想发布订阅 起码得有三个方法
 * 添加观察者（订阅者） 移除订阅者  通知订阅者
 */

public interface Subject {
    void attach(Observer observer);

    void detach(Observer observer);

    void notifyObserves(String message);
}
