package com.cxr.designpatterns.templateMethod;

public abstract class HummerModel {


    public abstract void start(); //发动

    public abstract void stop();  //停止

    public abstract void alarm(); //鸣笛

    public abstract void engineBoom(); //轰鸣

    public void run() { //车总归要跑
        this.start();
        this.engineBoom();

        //鸣笛与与否应该交给用户来判断
        if (this.isAlaram()) {
            this.alarm();
        }

        this.stop();
    }

    protected boolean isAlaram() {
        //默认的实现，这个方法是要用来重写的
        return true;
    }
}
