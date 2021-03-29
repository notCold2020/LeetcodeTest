package com.cxr.designpatterns.factory;

interface Phone {
    void getPhone();
}

interface Router {
    void getRouter();
}

interface IProductFactory {
    Phone phoneFactory();

    Router routerFactory();
}

class xiaomiPhone implements Phone {

    @Override
    public void getPhone() {
        System.out.println("小米手机");
    }
}

class xiaomiRouter implements Router {

    @Override
    public void getRouter() {
        System.out.println("小米路由器");
    }
}

class huaweiPhone implements Phone {

    @Override
    public void getPhone() {
        System.out.println("华为手机");
    }
}

class huaweiRouter implements Router {

    @Override
    public void getRouter() {
        System.out.println("华为路由器");
    }
}

class xiaomiFactory implements IProductFactory {

    @Override
    public Phone phoneFactory() {
        return new xiaomiPhone();
    }

    @Override
    public Router routerFactory() {
        return new xiaomiRouter();
    }
}

public class FactoryFactory {
    public static void main(String[] args) {
        //小米工厂生产出来的都是小米的东西
        Phone phone = new xiaomiFactory().phoneFactory();
    }
}
