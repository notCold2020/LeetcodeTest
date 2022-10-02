package com.cxr.designpatterns.RulesEngine;

import org.springframework.stereotype.Component;

/**
 * @Date 2022/5/9 9:55 下午
 * @Created by CiXingrui
 */
@Component
public class ChildrenFastival extends NodeAble {
    @Override
    public Integer getNodeNum() {
        return FastivalEnum.儿童节.getCode();
    }

    @Override
    public NodeContext execute(NodeContext nodeContext) {
        Integer itemPrice = nodeContext.getItemInfo().getItemPrice();


        nodeContext.setResult(nodeContext.getResult() + "儿童节打折成功！");

        return nodeContext;
    }
}
