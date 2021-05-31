package com.cxr.other.utilsSelf.copyUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class MyBeanUtils {

    public static final Logger LOGGER = LoggerFactory.getLogger(MyBeanUtils.class);

    /**
     * 浅拷贝
     * 将包含BeanA的list，转换为包含BeanB的list。应用场景示例：List<父类>转List<子类> sourceList -> targetClass
     *
     * @param sourceList
     * @param targetClass
     * @return
     */
    public static <T> List<T> copyList(List<?> sourceList, Class<T> targetClass) {
        if (sourceList == null) {
            return null;
        }

        List<T> targetList = new ArrayList<T>();

        for (Object tempSrc : sourceList) {
            try {
                T t = copyBean(tempSrc, targetClass);
                targetList.add(t);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return targetList;
    }

    /**
     * 浅拷贝，本质和Spring的BeanUtils一模一样  spring是前者拷贝至后者
     *
     * @param srcBean     待转换Bean
     * @param targetClass 目标Bean的Class
     * @param <T>         目标Bean
     * @return
     */
    public static <T> T copyBean(Object srcBean, Class<T> targetClass) {
        try {
            T t = targetClass.newInstance();
            BeanUtils.copyProperties(srcBean, t);
            return t;
        } catch (Exception e) {
            LOGGER.error("copyBean failed");
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 深拷贝（序列化的方式实现深拷贝）
     */
    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }

    /**
     * 升级版 可以传任意参数
     */
    public static <T> T deepCopyBean(T src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        T dest = (T) in.readObject();
        return dest;
    }
}
