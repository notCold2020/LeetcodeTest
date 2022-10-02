package com.cxr.designpatterns.RulesEngineBetter.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/5/12 5:10 下午
 * @Created by CiXingrui
 */
public enum NodeRunStateEnum {

    /*
     * false
     */
    FALSE((byte) 0),
    /*
     * true
     */
    TRUE((byte) 1),
    /*
     * none
     */
    NONE((byte) 2);


    private static final Map<Byte, NodeRunStateEnum> MAP = new HashMap<>();

    static {
        System.out.println("NodeRunStateEnum~~~~~");
        for (NodeRunStateEnum enums : NodeRunStateEnum.values()) {
            MAP.put(enums.getState(), enums);
        }
    }

    private final byte state;

    NodeRunStateEnum(byte state) {
        this.state = state;
    }

    public static NodeRunStateEnum getEnum(byte state) {
        return MAP.get(state);
    }

    public byte getState() {
        return state;
    }
}
