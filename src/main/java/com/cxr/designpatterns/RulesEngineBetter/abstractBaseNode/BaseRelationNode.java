package com.cxr.designpatterns.RulesEngineBetter.abstractBaseNode;

import com.cxr.designpatterns.RulesEngineBetter.model.IceLinkedList;

/**
 * @Date 2022/5/12 5:19 下午
 * @Created by CiXingrui
 *
 * 关系节点，应该具有获得改关系节点下的children的能力
 */
public abstract class BaseRelationNode extends BaseNode{

    /**
     * 只有关系节点才需要存储当前关系节点的children信息
     *
     * 因为关系节点需要获取所有的当前节点children信息，然后遍历，看看有几个返回true或者false
     */
    private IceLinkedList<BaseNode> children;

    protected BaseRelationNode() {
        children = new IceLinkedList<>();
    }


    public IceLinkedList<BaseNode> getChildren() {
        return children;
    }

    public void setChildren(IceLinkedList<BaseNode> children) {
        this.children = children;
    }
}
