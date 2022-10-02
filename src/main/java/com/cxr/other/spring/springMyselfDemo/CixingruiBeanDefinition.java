package com.cxr.other.spring.springMyselfDemo;

import lombok.Data;

/**
 * @Date 2022/5/21 4:12 下午
 * @Created by CiXingrui
 *
 * 其实beanDefination 就是bean的配置信息缓存
 * 总不能每次我们调用getBean方法都去用反射解析这个bean的信息吧
 */
@Data
public class CixingruiBeanDefinition {


    private Class clazz;

    private String scope;

}
