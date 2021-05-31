package com.cxr.designpatterns.strategyMethod;

public class SwingFlyBehavior implements  FlyBehavior {
    @Override
    public void fly() {
        System.out.println("---我用翅膀飞---");
    }
}
