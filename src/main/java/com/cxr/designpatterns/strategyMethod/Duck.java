package com.cxr.designpatterns.strategyMethod;

//抽象类：鸭子
public  abstract class Duck {
    FlyBehavior flyBehavior;
    public Duck(){//子类的构造函数中可以定义行为

    }


    //由子类去实现
    public abstract void display();

    //在本抽象类中已经自己实现了
    public void swim(){
        System.out.println("~~我会游泳~~");
    }

    //实例化对象时可以动态的改变对象的行为(比继承灵活性强)
    public void SetFlyBehavoir(FlyBehavior fb){
        flyBehavior=fb;
    }

    public void Fly(){
        flyBehavior.fly();
    }


}

