package com.cxr.designpatterns.RulesEngineBetter.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Date 2022/5/13 11:44 上午
 * @Created by CiXingrui
 */
@Data
@Accessors(chain=true)
public class NodeDo {

    /**
     * 每个节点都应该有个自己的节点id
     * 可以去cache里面通过节点id获取节点的信息
     */
    private String nodeId;

    /**
     * 节点类型
     */
    private NodeTypeEnum nodeTypeEnum;

    /**
     * 场景id 可以用场景id触发这一整条链路
     */
    private String scenesId;

    /**
     * 当前节点的子节点
     * 1,2,3
     */
    private String sonIds;

    /**
     * 节点描述
     * And-满1000减100
     */
    private String nodeDesc;

    /**
     * 节点配置信息 JSON
     * {"score":100,"key":"cost"}
     */
    private String nodeConf;

    /**
     * 节点的权限定类名
     * com.ice.test.flow.ScoreFlow
     */
    private String nodeRefrence;


}
