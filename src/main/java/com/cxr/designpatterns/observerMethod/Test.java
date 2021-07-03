package com.cxr.designpatterns.observerMethod;

/**
 * 核心思想：
 * 用个list存储订阅者。
 * 广播事件的时候遍历list调用实现类自己的方法
 *
 * 1.框架中哪里用到的
 * ApplicationListener接口里面的onApplicationEvent，一般都用MQ不用这玩意
 */
public class Test {
    public static void main(String[] args) {
        SubjectImpl subject = new SubjectImpl();
        //1.添加观察者
        subject.attach(new FirstObserver());
        subject.attach(new SecondObserver());
        //2.广播事件
        subject.notifyObserves("广播事件！！！");

    }
}
