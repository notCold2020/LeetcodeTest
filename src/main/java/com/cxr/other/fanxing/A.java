package com.cxr.other.fanxing;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

class A<T> {
    public static void main(String[] args) {

    }
    public A() {
        Class<? extends A> subClass = this.getClass();
        subClass.getGenericInterfaces();//获得这个对象实现的接口
        /**
         * Type是什么？
         * 1.ParameterizedType 参数化类型 就是泛型
         * 2.GenericArrayType 泛型数组
         * 3.TypeVariable<D> 类型改变量
         * 4.WildcardType 通配符类型   这四个接口都继承Type
         * 5.Class类也实现了Type接口
         *
         */
        Type genericSuperclass = subClass.getGenericSuperclass();// 得到 带泛型父类
        // 本质是ParameterizedTypeImpl，可以向下强转
        ParameterizedType parameterizedTypeSuperclass = (ParameterizedType) genericSuperclass;
        // 强转后可用的方法变多了，比如getActualTypeArguments()可以获取Class A<String>的泛型的实际类型参数
        Type[] actualTypeArguments = parameterizedTypeSuperclass.getActualTypeArguments();
        // 由于A类只有一个泛型，这里可以直接通过actualTypeArguments[0]得到子类传递的 实际类型参数
        System.out.println(actualTypeArguments[0].getTypeName());
        System.out.println(subClass.getName());
        System.out.println("新增用户：张三---当前用户：1人");
    }
}

class B<T> extends A<String> {
}

class C extends A<Integer> {
}
