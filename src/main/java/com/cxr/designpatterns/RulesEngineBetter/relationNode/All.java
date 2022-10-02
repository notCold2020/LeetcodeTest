package com.cxr.designpatterns.RulesEngineBetter.relationNode;

import com.cxr.designpatterns.RulesEngineBetter.abstractBaseNode.BaseNode;
import com.cxr.designpatterns.RulesEngineBetter.abstractBaseNode.BaseRelationNode;
import com.cxr.designpatterns.RulesEngineBetter.model.CommonContext;
import com.cxr.designpatterns.RulesEngineBetter.model.IceLinkedList;
import com.cxr.designpatterns.RulesEngineBetter.model.NodeRunStateEnum;

/**
 * @Date 2022/5/12 5:31 下午
 * @Created by CiXingrui
 * <p>
 * All节点，final不可以修改
 * 所有子节点都会执行
 * 有任意一个返回true该节点也是true
 * 没有true有一个节点是false则false
 * 没有true也没有false则返回none，所有子节点执行完毕终止
 */
public final class All extends BaseRelationNode {

    @Override
    public NodeRunStateEnum processNode(CommonContext commonContext) {

        IceLinkedList<BaseNode> children = this.getChildren();

        //是否有节点返回 true false
        boolean hasTrue = false;
        boolean hasFalse = false;

        //遍历当前children - 看仔细children不是一个list
        for (IceLinkedList.Node<BaseNode> first = children.getFirst(); first != null; first = first.next) {
            //当前节点信息
            BaseNode item = first.item;
            if (item != null) {
                //执行当前这个children的processNode方法
                NodeRunStateEnum nodeRunStateEnum = item.processNode(commonContext);

                //如果已经有true了，剩下的就不用判断了
                if (!hasTrue) {
                    hasTrue = nodeRunStateEnum == NodeRunStateEnum.TRUE;
                }
                if (!hasFalse) {
                    hasFalse = nodeRunStateEnum == NodeRunStateEnum.FALSE;
                }

            }
        }

        /**
         * 这里就是All节点的逻辑
         * 只要有节点返回true 当前的All节点就是true
         */
        if (hasTrue) {
            return NodeRunStateEnum.TRUE;
        }
        if (hasFalse) {
            return NodeRunStateEnum.FALSE;
        }
        return NodeRunStateEnum.NONE;

    }
}
