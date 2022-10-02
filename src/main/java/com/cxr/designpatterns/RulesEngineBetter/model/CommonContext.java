package com.cxr.designpatterns.RulesEngineBetter.model;

import lombok.Data;

import java.util.Map;

/**
 * @Date 2022/5/12 5:03 下午
 * @Created by CiXingrui
 */
@Data
public class CommonContext {

    private String traceId;

    /**
     * 节点配置的JSON入参就放在这个map里面
     *
     * {
     *     "scene":"充值-1",
     *     "roam":{
     *         "cost":120,
     *         "uid":18004
     *     }
     * }
     */
    private Map<String, Object> map;

    /**
     * 拼接执行链路
     */
    private StringBuilder stringBuilder = new StringBuilder();

}
