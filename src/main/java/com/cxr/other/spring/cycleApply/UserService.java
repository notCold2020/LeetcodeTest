package com.cxr.other.spring.cycleApply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

/**
 * @Date 2022/5/18 3:16 下午
 * @Created by CiXingrui
 */
//@Component
public class UserService {

    @Autowired
    @Lazy
    private OrderService orderService;

}
