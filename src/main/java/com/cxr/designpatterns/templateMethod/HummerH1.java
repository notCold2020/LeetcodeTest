package com.cxr.designpatterns.templateMethod;

//悍马H1
public class HummerH1 extends HummerModel {


    private boolean flag = true;

    @Override
    public void start() {
        System.out.println("H1发动……");
    }

    @Override
    public void stop() {
        System.out.println("H1停止……");
    }

    @Override
    public void alarm() {
        System.out.println("H1鸣笛……");
    }

    @Override
    public void engineBoom() {
        System.out.println("H1轰鸣……");
    }

    /**
     * 这个isAlarm方法俗称钩子方法。
     * 我们可以在这个extends HummerModel的类的方法里面
     * 动态的修改公共的方法
     */
    @Override
    protected boolean isAlaram() {
        return this.flag;
    }

    protected void setFlag(boolean flag) {
        this.flag = flag;
    }

}

