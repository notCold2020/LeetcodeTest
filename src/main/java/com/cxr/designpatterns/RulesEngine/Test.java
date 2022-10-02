package com.cxr.designpatterns.RulesEngine;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Date 2022/5/9 10:27 下午
 * @Created by CiXingrui
 */
public class Test {

    static Map<Integer, NodeAble> staticNodeMap = NodeRegister.staticNodeMap;


    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(Appconfig.class);

        List<Integer> integers = Arrays.asList(1, 1, 1, 1, 1);

        NodeContext nodeContext = new NodeContext();

        for (Integer integer : integers) {
            NodeAble nodeAble = staticNodeMap.get(integer);
            nodeContext = nodeAble.execute(nodeContext);
        }

        System.out.println(JSON.toJSONString(nodeContext));
    }
}
