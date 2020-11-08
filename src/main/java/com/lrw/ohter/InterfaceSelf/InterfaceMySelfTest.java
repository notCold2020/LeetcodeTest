package com.lrw.ohter.InterfaceSelf;

import java.lang.reflect.Method;

public class InterfaceMySelfTest {

    @InterfaceMySelf(name = "哆啦A梦", abilityCount = 2, abilityNames = {"任意门", "竹蜻蜓"})
    public void people1() {
    }

    @InterfaceMySelf(name = "大雄", abilityCount = 1, abilityNames = {"射击"})
    public void people2() {
    }

    @InterfaceMySelf
    public void people3() {
    }

    public static void main(String[] args) {
        Class<InterfaceMySelfTest> interfaceMySelfTestClass = InterfaceMySelfTest.class;
        Method[] methods = interfaceMySelfTestClass.getMethods();
        for (Method method : methods) {
            if(method.isAnnotationPresent(InterfaceMySelf.class)){
                InterfaceMySelf annotation = method.getAnnotation(InterfaceMySelf.class);
                System.out.println("我是："+method.getName()+",我叫："+annotation.name()+",我有："+
                        annotation.abilityCount()+"个技能，分别是：");
                String[] strings = annotation.abilityNames();
                for(String s:strings){
                    System.out.println(s);
                }
                System.out.println("==============");

            }
        }

    }

}
