package com.cxr.other.strangeDemo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @Author: CiXingrui
 * @Create: 2021/9/11 3:33 下午
 */
public class InnerClassDemo {

    /**
     * 1.为什么这里可以不写接口的实现类 直接在接口后面加大括号呢？
     * <p>
     * 这是一个匿名内部类，反编译之后发现匿名内部类的名字就叫InnerClassDemo1,接口是必须要有实现类的，所以就高了个匿名内部类来实现接口
     * 个人理解这就是一个类似于语法糖的东西
     * <p>
     * 同理：抽象类也一样
     */
    void test1() {
        TestIface testIface = new TestIface() {

            @Override
            public void userName() {

            }

            @Override
            public void userTel() {

            }
        };
    }

    /**
     * 2.new了一个实实在在的类，后面跟着大括号
     * <p>
     * 其实就是Map中的put方法被重写 了，所以当 map2.put的时候，会自动调用我们重写之后的方法
     * start_key没有对应的 value
     */
    void test2() {
        Map<String, String> map2 = new HashMap<String, String>() {
            @Override
            public String put(String var1, String var2) {
                var1 = "key_value";//a行
                var2 = "重写后的值";//b行
                return super.put(var1, var2);
            }
        };
        map2.put("start_key", "不知道start_key的值是不是这个");
        System.out.println("找到start_key的值：      " + map2.get("start_key"));
        System.out.println("虽然没有明显把key_value当key赋值，尝试尝试key_value的值：      " + map2.get("key_value"));
    }


    /**
     * 3.俩{{}}
     * 我感觉就是在实例化后 调用内部{}里面 的方法，给实例化之后的对象赋值
     */
    void test3() {
        List<String> list = new ArrayList<String>() {
            {
                add("a");
                add("b");
            }
        };
        System.out.println("list长度：  " + list.size());


        Map<String, String> map = new HashMap<String, String>() {
            {
                put("haha", "heiehi");
            }
        };
        System.out.println(map.get("haha"));

    }

    /**
     * 4.为什么TypeReference需要后面跟一个大括号？
     *
     * 因为TypeReference的构造方法是protect的，别人不能随意的new它。
     * 所以只能搞一个匿名内部类去继承TypeReference（TypeReference本身是public的）
     *
     * 就是明确指定反序列化类型的
     */
    void test4() {
        List<String> list = Arrays.asList("张三 ", "李四", "王五 ");
        Type type = new TypeReference<List<String>>() {
        }.getType();
        String s = JSONObject.toJSONString(list);
        List<String> list1 = JSONObject.parseObject(s, type);
        System.out.println(list1);
    }
}


interface TestIface {
    void userName();

    void userTel();
}
