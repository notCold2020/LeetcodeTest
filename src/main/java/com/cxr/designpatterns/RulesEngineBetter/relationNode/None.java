package com.cxr.designpatterns.RulesEngineBetter.relationNode;

import com.cxr.designpatterns.RulesEngineBetter.abstractBaseNode.BaseNode;
import com.cxr.designpatterns.RulesEngineBetter.abstractBaseNode.BaseRelationNode;
import com.cxr.designpatterns.RulesEngineBetter.model.CommonContext;
import com.cxr.designpatterns.RulesEngineBetter.model.IceLinkedList;
import com.cxr.designpatterns.RulesEngineBetter.model.NodeRunStateEnum;

/**
 * @Date 2022/5/12 9:32 下午
 * @Created by CiXingrui
 * <p>
 * None :无论节点返回什么都会被执行 并且返回None
 */
public final class None extends BaseRelationNode {
    @Override
    public NodeRunStateEnum processNode(CommonContext commonContext) {

        IceLinkedList<BaseNode> children = this.getChildren();

        for (IceLinkedList.Node<BaseNode> listNode = children.getFirst(); listNode != null; listNode = listNode.next) {
            BaseNode node = listNode.item;
            if (node != null) {
                //这里不关心返回值了
                node.processNode(commonContext);
            }
        }

        return NodeRunStateEnum.NONE;
    }
}
