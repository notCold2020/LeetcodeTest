package com.cxr.designpatterns.RulesEngine;

import lombok.Data;

/**
 * @Date 2022/5/9 9:52 下午
 * @Created by CiXingrui
 */
@Data
public class NodeContext {

    private String errorMsg;

    private String traceId;

    /**
     * 商品信息
     */
    private ItemInfo itemInfo = new ItemInfo();

    private String result = "";
}
