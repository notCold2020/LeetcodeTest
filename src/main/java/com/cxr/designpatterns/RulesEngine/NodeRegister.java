package com.cxr.designpatterns.RulesEngine;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/5/9 10:21 下午
 * @Created by CiXingrui
 */
@Component
@Data
public class NodeRegister {

    @Autowired(required = false)
    private Map<String, NodeAble> nodeMap;

    public static Map<Integer, NodeAble> staticNodeMap = new HashMap<>();

    @PostConstruct
    public void init() {
        nodeMap.forEach((key, entity) -> {
            Integer nodeNum = entity.getNodeNum();
            staticNodeMap.put(nodeNum, entity);
        });
    }


}
