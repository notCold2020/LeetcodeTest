package com.cxr.designpatterns.RulesEngineBetter.useModel;

import com.cxr.designpatterns.RulesEngineBetter.leafNode.BaseResultNode;
import com.cxr.designpatterns.RulesEngineBetter.model.CommonContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Date 2022/5/15 12:14 下午
 * @Created by CiXingrui
 */
@Data
@Slf4j
public class AmountResult extends BaseResultNode {

    /**
     * 项目启动的时候就填充当前bean
     * {"key":"uid","value":5}
     */
    private String key;

    private double value;

    @Override
    public boolean doResultNode(CommonContext commonContext) {

        String uid = commonContext.getMap().get(key).toString();

        //用uid发放余额
        log.info(uid + ":发放余额成功");

        return true;
    }
}
