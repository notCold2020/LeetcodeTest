package com.cxr.designpatterns.templateMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 1。模版方法模式优缺点：
 *      1.行为由父类控制 子类实现
 *      2.提取代码公共部分 易于扩展
 *      缺点：不太容易修改公共方法
 *
 * 2。和策略模式的区别
 *          策略模式缺点：客户端需要知道全部策略 如果策略过多 可读性较差
 *                 优点：易于扩展吧。。
 *
 *
 * 大白话就是：
 * 精髓在于 在模板方法里面 也就是抽象类里面 我们需要用一个抽象类/通过子类重写父类方法（这两种本质也差不多，就是覆盖掉我们的模板，让我们自定义）来占个坑位，
 * 核心思想就是用一个东西占模板里面的坑 我们自定义这个坑 要做什么
 *
 */
public class Test {
    public static void main(String[] args) throws IOException {
        System.out.println("----H1型悍马----");
        System.out.println("是否需要喇叭声响？ 0-不需要  1-需要");
        String type = new BufferedReader(new InputStreamReader(System.in)).readLine();
        HummerH1 h1 = new HummerH1();
        if (type.equals("0")) {
            h1.setFlag(false);
        }
        h1.run();
    }
}
