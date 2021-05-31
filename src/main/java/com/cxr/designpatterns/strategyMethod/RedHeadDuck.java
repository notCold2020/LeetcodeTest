package com.cxr.designpatterns.strategyMethod;

/**
 * 红头鸭：继承基类Duck
 */
public class RedHeadDuck extends Duck {

    public RedHeadDuck() {
        flyBehavior = new SwingFlyBehavior();
    }

    @Override
    public void display() {
        System.out.println("我是独一无二的，我是红头鸭。我会飞");
    }
}

