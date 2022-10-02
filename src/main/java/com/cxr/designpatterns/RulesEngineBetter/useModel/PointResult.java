package com.cxr.designpatterns.RulesEngineBetter.useModel;

import com.cxr.designpatterns.RulesEngineBetter.leafNode.BaseResultNode;
import com.cxr.designpatterns.RulesEngineBetter.model.CommonContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Date 2022/5/15 12:19 下午
 * @Created by CiXingrui
 */
@Data
@Slf4j
public class PointResult extends BaseResultNode {

    /**
     * {"key":"uid","value":10}
     */
    private String key;

    private double value;


    @Override
    public boolean doResultNode(CommonContext commonContext) {
        String uid = commonContext.getMap().get(key).toString();
        log.info(uid + ":加积分成功");
        return false;
    }
}
