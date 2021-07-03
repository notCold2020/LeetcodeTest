package com.cxr.designpatterns.observerMethod;

import java.util.ArrayList;
import java.util.List;

public class SubjectImpl implements Subject {
    //1.得有个存储订阅者的容器
    List<Observer> observerList = new ArrayList<>();

    @Override
    public void attach(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObserves(String message) {
        for (Observer observer : observerList) {
            /**
             * 这调用的是FirstObserver/SecondObserver重写的方法
             */
            observer.update(message);
        }
    }
}
