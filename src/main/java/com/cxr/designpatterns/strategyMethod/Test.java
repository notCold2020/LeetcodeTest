package com.cxr.designpatterns.strategyMethod;

/**
 * 主类：模拟鸭子
 *
 * 1.核心思想：把经常会变化的抽取出来，比如fly接口，单独给实现类。
 *
 * 策略模式优化了什么？
 * 一个类的行为或其算法可以在运行时更改。说人话就是，运行时候，我传给你不同的参数，你来执行不同的方法。
 * 这也就是我们说的"策略"。所以无论是用Map 还是接口（fly）的不同实现类。本质上就是 "策略"
 *
 */
public class Test {
    public static void main(String[] args) {
        //父类为Duck，屏蔽了超类的差别性
        Duck redHeadDuck = new RedHeadDuck();

        redHeadDuck.display();
        redHeadDuck.swim();

//        redHeadDuck.SetFlyBehavoir(new SwingFlyBehavior()); 这样给个默认的实现也行
        redHeadDuck.SetFlyBehavoir(new FlyBehavior() {
            @Override
            public void fly() {
                System.out.println("我会自定义的飞");
            }
        });
        redHeadDuck.Fly();
    }
}

