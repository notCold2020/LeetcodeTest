package com.cxr.designpatterns.RulesEngineBetter.relationNode;

import com.cxr.designpatterns.RulesEngineBetter.abstractBaseNode.BaseNode;
import com.cxr.designpatterns.RulesEngineBetter.abstractBaseNode.BaseRelationNode;
import com.cxr.designpatterns.RulesEngineBetter.model.CommonContext;
import com.cxr.designpatterns.RulesEngineBetter.model.IceLinkedList;
import com.cxr.designpatterns.RulesEngineBetter.model.NodeRunStateEnum;

/**
 * @Date 2022/5/12 9:06 下午
 * @Created by CiXingrui
 * <p>
 * Any
 * 子节点有true就终止
 * 类比于 ||
 * 有true就是true 都false裁false
 */
public class Any extends BaseRelationNode {
    @Override
    public NodeRunStateEnum processNode(CommonContext commonContext) {

        IceLinkedList<BaseNode> children = this.getChildren();


        boolean hasFalse = false;

        for (IceLinkedList.Node<BaseNode> first = children.getFirst(); first != null; first = first.next) {
            BaseNode item = first.item;

            if (item != null) {
                NodeRunStateEnum nodeRunStateEnum = item.processNode(commonContext);

                //如果有节点返回true 直接就终止
                if (nodeRunStateEnum == NodeRunStateEnum.TRUE) {
                    return NodeRunStateEnum.TRUE;
                }

                if (!hasFalse) {
                    //说明目前没有过hasFalse
                    hasFalse = nodeRunStateEnum == NodeRunStateEnum.FALSE;
                }

            }
        }

        if (hasFalse) {
            return NodeRunStateEnum.FALSE;
        }
        return NodeRunStateEnum.NONE;

    }
}
