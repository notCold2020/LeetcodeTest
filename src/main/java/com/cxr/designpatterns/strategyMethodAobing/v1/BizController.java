package com.cxr.designpatterns.strategyMethodAobing.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 模拟一次http调用
 */
public class BizController {

    @Autowired
    private BizService bizService;

    /**
     * 传入校验1 返回对校验1执行业务逻辑1
     */
    @PostMapping("/v1/biz/testSuper")
    public String test2(String order) {
        return bizService.getCheckResultSuper(order);
    }
}
