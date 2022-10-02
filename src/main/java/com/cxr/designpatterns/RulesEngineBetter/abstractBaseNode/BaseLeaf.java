package com.cxr.designpatterns.RulesEngineBetter.abstractBaseNode;

import com.cxr.designpatterns.RulesEngineBetter.model.CommonContext;
import com.cxr.designpatterns.RulesEngineBetter.model.NodeRunStateEnum;

/**
 * @Date 2022/5/12 5:22 下午
 * @Created by CiXingrui
 *
 * 叶子节点负责真正执行业务逻辑
 */
public abstract class BaseLeaf extends BaseNode{

    @Override
    public NodeRunStateEnum processNode(CommonContext commonContext) {
        return doLeaf(commonContext);
    }

    /**
     * 这个交给具体子类实现，别人用的话用processNode
     */
    protected abstract NodeRunStateEnum doLeaf(CommonContext commonContext);
}
