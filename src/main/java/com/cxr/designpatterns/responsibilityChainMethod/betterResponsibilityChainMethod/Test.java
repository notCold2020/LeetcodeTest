package com.cxr.designpatterns.responsibilityChainMethod.betterResponsibilityChainMethod;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/5/9 12:33 下午
 * @Created by CiXingrui
 */
public class Test {

    /**
     * 核心思想就是 抽象类里面的抽象方法是活的，抽象类里面的普通方法
     *
     * 并且这里可以在最外面弄个List<Componment> 组件的列表，比如这个列表包括了 黑名单 白名单 风控
     * 然后list.stream().filter(comtext).foreach(m -> handler())
     * 通过上下文过滤一部分 然剩下的循环调用 更方便
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(Appconfig.class);

        ChainPatternDemo bean = annotationConfigApplicationContext.getBean(ChainPatternDemo.class);
        List<String> request = new ArrayList<>();
        List<String> response = new ArrayList<>();

        List<String> exec = bean.exec(request, response);
        System.out.println(JSON.toJSONString(exec));
    }


}
