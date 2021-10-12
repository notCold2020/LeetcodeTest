package com.cxr.other.strangeDemo;

import com.alibaba.fastjson.JSONObject;

public class TestFastJson {
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        Student student = new Student();
        student.setName("cixingrui");
        /**
         * 这个s里面 就会有个莫名其妙的num属性(因为我们有getNum方法) -> 序列化的时候是通过get方法拿到值的
         */
        String s = JSONObject.toJSONString(student);

        /**
         * 反序列化通过set方法把值set进去
         */
        Test test = JSONObject.parseObject(s, Test.class);
        Student student1  = JSONObject.parseObject(s, Student.class);
        System.out.println(test);
    }
}

class Student {
    private String name;
    private Teacher teacher;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //没有Num属性，但是getNum方法中有异常，所有get开头的方法序列化时会被调用
    public String getNum() {
//        return teacher.getName();
        return "Numxxx";
    }

    /**
     * Num()方法中有异常,但是序列化正常
     * 序列化和反序列化都不会调用这个方法
     */
    public String Num() {
        return teacher.getName();
    }

    /**
     * setNum方法没有关系，即使其中有异常，序列化也没有问题，反序列化也没有问题
     * 因为这个set方法没有入参
     *
     * 而且就算有入参 参数多了多少少了都不行
     * 必须得是我们常规意义上的set方法
     * 一模一样
     */
    public void setNum() {
        teacher.getName();
    }

}

class Test{
    private String name;
    private String num;

    public void setName(String name) {
        this.name = name;
    }

    public void setNum(String num) {
        this.num = num;
    }
}

class Teacher {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
