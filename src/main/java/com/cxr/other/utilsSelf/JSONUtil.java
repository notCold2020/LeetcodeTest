package com.cxr.other.utilsSelf;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cxr.other.demo.entriy.User;

import java.util.Arrays;

public class JSONUtil {

    public static void main(String[] args) {
        test01();
    }

    /**
     * Json不存String类型，那么代码里面getString能拿到吗? ---> 也能
     * <p>
     * getString本质上就是get到Object然后toString
     */
    private static void test01() {
        String user = beanToJsonString(new User());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key1", user);
        jsonObject.put("key2", Arrays.asList("user1", "user2"));

        String key1 = jsonObject.getString("key1");
        String key2 = jsonObject.getString("key2");
    }

    /*
     * bean转化为某种class
     * 可以是String
     * 也可以是其他的bean(有点像浅拷贝)
     */
    public static <T, E> T convetToJavaBean(E source, Class<T> targetClass) {
        T result = null;
        if (source == null) {
            return result;
        }
        try {
            String jsonStr = JSONObject.toJSONString(source, SerializerFeature.DisableCircularReferenceDetect);
            result = JSONObject.parseObject(jsonStr, targetClass);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * JSON类型的String转换为bean
     */
    public static <T, E> T stringToJavaBen(String source, Class<T> targetClass) {
        T result = null;
        if (source == null) {
            return result;
        }
        try {
            result = JSONObject.parseObject(source, targetClass);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * bean -> String类型的json
     */
    public static <T> String beanToJsonString(T source) {
        String result = null;
        if (source == null) {
            return result;
        }
        try {
            result = JSONObject.toJSONString(source, SerializerFeature.DisableCircularReferenceDetect);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}



