package com.cxr.designpatterns.RulesEngineBetter.abstractBaseNode;

import com.cxr.designpatterns.RulesEngineBetter.model.CommonContext;
import com.cxr.designpatterns.RulesEngineBetter.model.NodeRunStateEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Date 2022/5/12 5:08 下午
 * @Created by CiXingrui
 * <p>
 * 基础节点，只要是个节点 都应该能执行流程
 */
@Data
@Setter
@Getter
public abstract class BaseNode {

    /**
     * 只是设置个节点名字
     */
    private String nodeName;

    private String nodeId;

    public abstract NodeRunStateEnum processNode(CommonContext commonContext);


    public NodeRunStateEnum process(CommonContext commonContext) {

        NodeRunStateEnum nodeRunStateEnum = processNode(commonContext);
        //收集日志
        collectLog(commonContext, nodeRunStateEnum);

        return nodeRunStateEnum;
    }

    private void collectLog(CommonContext commonContext, NodeRunStateEnum nodeRunStateEnum) {

        String state = "";

        switch (nodeRunStateEnum) {
            case FALSE:
                state = "执行失败";
                break;
            case TRUE:
                state = "执行成功";
                break;
        }
        StringBuilder stringBuilder = commonContext.getStringBuilder();

        stringBuilder.append("【").append(this.getNodeId() + ":" + this.getNodeName() + state).append("】-");
    }
}
