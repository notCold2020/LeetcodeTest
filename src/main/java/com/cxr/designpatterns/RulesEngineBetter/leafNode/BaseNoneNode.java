package com.cxr.designpatterns.RulesEngineBetter.leafNode;

import com.cxr.designpatterns.RulesEngineBetter.abstractBaseNode.BaseLeaf;
import com.cxr.designpatterns.RulesEngineBetter.model.CommonContext;
import com.cxr.designpatterns.RulesEngineBetter.model.NodeRunStateEnum;

/**
 * @Date 2022/5/13 11:38 上午
 * @Created by CiXingrui
 */
public abstract class BaseNoneNode extends BaseLeaf {

    public abstract void doNoneNode(CommonContext commonContext);

    @Override
    protected NodeRunStateEnum doLeaf(CommonContext commonContext) {

        doNoneNode(commonContext);

        return NodeRunStateEnum.NONE;

    }
}
