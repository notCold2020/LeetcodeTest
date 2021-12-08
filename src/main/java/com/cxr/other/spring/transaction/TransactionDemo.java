package com.cxr.other.spring.transaction;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: CiXingrui
 * @Create: 2021/10/15 10:59 上午
 */
@Component
public class TransactionDemo {

    @Transactional(rollbackFor = Exception.class)
    public void testTransaction(){

    }
}
