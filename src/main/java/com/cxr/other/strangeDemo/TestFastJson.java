package com.cxr.other.strangeDemo;

import com.alibaba.fastjson.JSONObject;

/**
 * 对象 --> json -->  对象
 *    序列化   反序列化
 *
 *  isTenant属性-->IDEA自动生成getTenant方法-->这个方法生成的json对应的是tenannt属性
 *  -->反序列化对应的对象需要的是setTenant方法-->去要被反序列化生成的对象里面找setTenant方法
 */
public class TestFastJson {
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        Student student = new Student();
        student.setName("cixingrui");
        student.setStudent(true);
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
    /**
     * 这个isStudent，IDE自动帮我们生成的set get方法不是叫setIsStudent和getIsStudent
     * 而是叫setStudent和isStudent(自动省略了属性中的is)
     *
     * 序列化的时候，FastJson是根据get方法生成的属性。反序列化的时候，是根据set方法。(序列化-->要拿到对象的属性变成json-->get方法)
     * 序列化完成后我们的isStudent属性就会变成student属性
     *
     * 就算是包装类➡️➡️自动生成的set/get方法也不过是setStudent和getStudent
     *
     * 这就导致了序列化的时候，isStudent属性变成了Student属性，再反序列化的时候,把反序列化赋值了。但是反序列化成的对象里面没有student属性
     * 有的是isStudent属性，这时候如果我们是boolean 就是false,如果是Boolean就是null
     *
     * 解决方案：
     * 1.修改get、set方法为setIsStudent和getIsStudent
     * 2.lombok自动生成
     * 3.@JSONField(name = "isSuccess")
     *     private boolean isSuccess;
     */
    private boolean isStudent;

    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        isStudent = student;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 这里set并不会生成一个name2属性 而是name2属性调用setName2方法给我们setName2方法里面的this.name赋值
     * 所以反序列化的原理就是如果json里面有"name111":"123" 就会调用setName111方法吧123作为入参传进去(形参是什么无所谓)
     *
     * 这里我们因为json里面有name2，这里也有setname2方法，还有入参，自然就会调用方法，把name属性覆盖掉了
     */
    public void setName2(String sda) {
        this.name = sda;
    }

    public String getName2() {
        return "我是name2";
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
