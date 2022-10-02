package com.cxr.designpatterns.RulesEngineBetter.useModel;

import com.cxr.designpatterns.RulesEngineBetter.leafNode.BaseFlowNode;
import com.cxr.designpatterns.RulesEngineBetter.model.CommonContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Date 2022/5/15 11:59 上午
 * @Created by CiXingrui
 *
 * key代表场景 score代表临界值
 */
@Data
@Slf4j
public class ScoreFlow extends BaseFlowNode {

    private double score;

    /**
     * {"score":100,"key":"cost"}
     */
    private String key;  //只有key字段是活的，score代表的是当前类的写死的信息


    @Override
    public boolean doFlowNode(CommonContext commonContext) {

        double userScore = Double.parseDouble(commonContext.getMap().get(key).toString());

        //flow节点只需返回true/false
        if (userScore > score) {
            return true;
        }

        return false;
    }
}
