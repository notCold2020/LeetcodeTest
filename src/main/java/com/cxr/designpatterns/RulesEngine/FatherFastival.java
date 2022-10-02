package com.cxr.designpatterns.RulesEngine;

import org.springframework.stereotype.Component;

@Component
public class FatherFastival extends NodeAble {
    @Override
    public Integer getNodeNum() {
        return FastivalEnum.父亲节.getCode();
    }

    @Override
    public NodeContext execute(NodeContext nodeContext) {
        Integer itemPrice = nodeContext.getItemInfo().getItemPrice();

        nodeContext.setResult(nodeContext.getResult() + "父亲节打折成功！");

        return nodeContext;
    }
}