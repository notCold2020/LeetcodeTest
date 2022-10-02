package com.cxr.designpatterns.RulesEngine;

/**
 * @Date 2022/5/9 9:51 下午
 * @Created by CiXingrui
 */
public abstract class NodeAble {

    public abstract Integer getNodeNum();

    public abstract NodeContext execute(NodeContext nodeContext);

}
