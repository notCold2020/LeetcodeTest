package com.cxr.designpatterns.strategyMethod;

/**
 * 主类：模拟鸭子
 *
 * 1.核心思想：把经常会变化的抽取出来，比如fly接口，单独给实现类。
 *
 * 策略模式优化了什么？
 * 一个类的行为或其算法可以在运行时更改。说人话就是，运行时候，我传给你不同的参数，你来执行不同的方法。
 * 这也就是我们说的"策略"。所以无论是用Map 还是接口（fly）的不同实现类。本质上就是 "策略"
 * 针对不同的策略做出对应行为，达到行为解偶
 *
 * 2.线程池中的策略模式
 * 就是拒绝策略，拒绝策略都实现了RejectedExecutionHandler接口,这个接口里面有真正的拒绝策略（实现）。用的时候直接new 实现类。对标
 * 下面的：
 *         redHeadDuck.SetFlyBehavoir(new FlyBehavior() {
 *             @Override
 *             public void fly() {
 *                 System.out.println("我会自定义的飞");       ----> 详细一点主要是这个策略new FlyBehavior()
 *             }
 *         });
 * 这不也是给一个策略吗？
 *
 */
public class Test {
    public static void main(String[] args) {
        //父类为Duck，屏蔽了超类的差别性
        Duck redHeadDuck = new RedHeadDuck();

        redHeadDuck.display();
        redHeadDuck.swim();

//        redHeadDuck.SetFlyBehavoir(new SwingFlyBehavior()); 这样给个默认的实现也行 目的就是覆盖FlyBehavior实现类里面的默认方法
        redHeadDuck.SetFlyBehavoir(new FlyBehavior() {
            @Override
            public void fly() {
                System.out.println("我会自定义的飞");
            }
        });
        redHeadDuck.Fly();
    }
}

