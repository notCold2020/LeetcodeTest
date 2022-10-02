package com.cxr.designpatterns.RulesEngineBetter.relationNode;

import com.cxr.designpatterns.RulesEngineBetter.abstractBaseNode.BaseNode;
import com.cxr.designpatterns.RulesEngineBetter.abstractBaseNode.BaseRelationNode;
import com.cxr.designpatterns.RulesEngineBetter.model.CommonContext;
import com.cxr.designpatterns.RulesEngineBetter.model.IceLinkedList;
import com.cxr.designpatterns.RulesEngineBetter.model.NodeRunStateEnum;

/**
 * @Date 2022/5/12 9:22 下午
 * @Created by CiXingrui
 * <p>
 * And 全部为true 才是true
 * 遇到false就终止  对标 &&
 */
public final class And extends BaseRelationNode {

    @Override
    public NodeRunStateEnum processNode(CommonContext commonContext) {

        IceLinkedList<BaseNode> children = this.getChildren();

        boolean hasTrue = false;

        for (IceLinkedList.Node<BaseNode> first = children.getFirst(); first != null; first = first.next) {
            BaseNode item = first.item;

            NodeRunStateEnum nodeRunStateEnum = item.processNode(commonContext);

            if (nodeRunStateEnum == NodeRunStateEnum.FALSE) {
                //遇到false就终止
                return NodeRunStateEnum.FALSE;
            }

            if (!hasTrue) {
                //hasTrue没变更过
                hasTrue = nodeRunStateEnum == NodeRunStateEnum.TRUE;
            }

        }

        if (hasTrue) {
            //没出现过false被终止，并且出现过true，说明全为true
            return NodeRunStateEnum.TRUE;
        }
        return NodeRunStateEnum.NONE;
    }
}
