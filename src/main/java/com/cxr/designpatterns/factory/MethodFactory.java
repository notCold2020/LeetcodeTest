package com.cxr.designpatterns.factory;

/**
 * 比如新增摩拜类 实现Car接口 新增摩拜工厂类 实现CarFactory接口 没有改动原来的代码！
 * 但是也很麻烦 要写很多类
 */
interface Car {
    Car getCar();
}

class WuLing implements Car {

    @Override
    public Car getCar() {
        return null;
    }
}

class BaoMa implements Car {

    @Override
    public Car getCar() {
        return null;
    }
}

public class MethodFactory {
    public static void main(String[] args) {
        Car wuling = new WuLing().getCar();
    }

}
