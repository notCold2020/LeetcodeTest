package com.cxr.designpatterns.RulesEngineBetter.leafNode;

import com.cxr.designpatterns.RulesEngineBetter.abstractBaseNode.BaseLeaf;
import com.cxr.designpatterns.RulesEngineBetter.model.CommonContext;
import com.cxr.designpatterns.RulesEngineBetter.model.NodeRunStateEnum;

/**
 * @Date 2022/5/12 9:50 下午
 * @Created by CiXingrui
 * <p>
 * 抽象的flow节点 -- 用来直接也业务流程节点使用
 */
public abstract class BaseFlowNode extends BaseLeaf {

    //用来给子类实现 - 真实业务逻辑
    public abstract boolean doFlowNode(CommonContext commonContext);

    /**
     * 这种逻辑意义在于如果有人调用了BaseNode的processNode方法
     * 那么一定会走一遍 processNode -- doLeaf -- doFlowNode 的流程
     * 方便加一些逻辑 - 比如日志
     */
    @Override
    protected NodeRunStateEnum doLeaf(CommonContext commonContext) {

        if (doFlowNode(commonContext)) {
            return NodeRunStateEnum.TRUE;
        }
        return NodeRunStateEnum.FALSE;
    }
}
