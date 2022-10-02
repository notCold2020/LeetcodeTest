package com.cxr.designpatterns.RulesEngineBetter.useModel;

import com.cxr.designpatterns.RulesEngineBetter.leafNode.BaseNoneNode;
import com.cxr.designpatterns.RulesEngineBetter.model.CommonContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @Date 2022/5/15 12:29 下午
 * @Created by CiXingrui
 */
@Data
@Slf4j
public class TimeChangeNone extends BaseNoneNode {

    private Date time;

    @Override
    public void doNoneNode(CommonContext commonContext) {
        log.info("执行了timeChangeNone");
    }
}
