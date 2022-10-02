package com.cxr.other.spring.asyncDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author: CiXingrui
 * @Create: 2021/10/25 5:34 下午
 */
@Service
public class AsyncService {

    @Autowired
    @Lazy
    private AsyncService asyncService;


    public void test01() throws InterruptedException {
        asyncService.test02();
//        asyncService.test02();
    }


    @Async
    public void test02() throws InterruptedException {
        Thread.sleep(5000L);
        System.out.println("test02🆗");
    }
}
