package com.cxr.other;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListNode {
    public int val;
    public ListNode next;

    ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class clazz = classLoader.loadClass("com.cxr.other.PrivateCar");

        PrivateCar privateCar = (PrivateCar) clazz.newInstance();
        Field colorField = clazz.getDeclaredField("color");

        //权限压制  取消Java语言访问检查以访问private属性
        colorField.setAccessible(true);
        colorField.set(privateCar, "红色");

        Method method = clazz.getDeclaredMethod("drive", (Class[]) null);

        method.setAccessible(true);
        method.invoke(privateCar, (Class[]) null);
    }

}

class PrivateCar {

    private String color;

    private void drive() {



        System.out.println("drive private car! the color is :" + color);
    }


    public static void main(String[] args) {
        boolean b = phoneCheckValue("86-15545667676", "^(\\+?0?86\\-?)?1[345789]\\d{9}$", "^(\\+?0?65\\-?)?\\d{10}$");
        System.out.println(b);

    }

    public static boolean phoneCheckValue(String msg, String... enums) {
        for (String item : enums) {
            Matcher matcher = Pattern.compile(item).matcher(msg);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }
}

