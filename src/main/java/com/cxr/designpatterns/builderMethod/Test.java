package com.cxr.designpatterns.builderMethod;

/**
 * 我理解就是创建复杂对象用的。
 * 比如我们要盖房子
 * 1.房子：估计需要四个操作，具体什么操作，不重要
 * 2.builder:建造者，抽象类，把房子注入builer
 * 3.builderImpl:真正开始建造，打地基，刷水泥等等
 * 4.导演类：安排builder来建造。
 */
public class Test {
    public static void main(String[] args) {
        Director director = new Director();
        Product create = director.create(new ConcreteBuilder());
        System.out.println(create.toString());
    }
}
