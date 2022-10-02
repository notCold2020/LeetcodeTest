package com.cxr.designpatterns.RulesEngineBetter.leafNode;

import com.cxr.designpatterns.RulesEngineBetter.abstractBaseNode.BaseLeaf;
import com.cxr.designpatterns.RulesEngineBetter.model.CommonContext;
import com.cxr.designpatterns.RulesEngineBetter.model.NodeRunStateEnum;

/**
 * @Date 2022/5/13 11:35 上午
 * @Created by CiXingrui
 */
public abstract class BaseResultNode extends BaseLeaf {

    public abstract boolean doResultNode(CommonContext commonContext);

    @Override
    protected NodeRunStateEnum doLeaf(CommonContext commonContext) {

        if (doResultNode(commonContext)) {
            return NodeRunStateEnum.TRUE;
        }

        return NodeRunStateEnum.FALSE;
    }
}
