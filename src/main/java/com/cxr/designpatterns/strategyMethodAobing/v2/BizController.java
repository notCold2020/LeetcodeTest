package com.cxr.designpatterns.strategyMethodAobing.v2;

import com.cxr.designpatterns.strategyMethodAobing.v2.BizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟一次http调用
 */
public class BizController {

    @Autowired
    private BizService bizService;


    /**
     * 只不过修改了map的key的格式
     * 传入order=订单  level = 3
     */
    @PostMapping("/v1/biz/testMuti")
    public String test1(String order, Integer level) {
        return bizService.getCheckResultMuti(order, level);
    }
}
