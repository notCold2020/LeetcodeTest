package com.cxr.other.spring.asyncDemo;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author: CiXingrui
 * @Create: 2021/10/25 5:34 下午
 */
@Service
public class AsyncServiceImpl implements AsyncService {

    @Override
    @Async
    public void test01() {
        System.out.println("AsyncServiceImpl线程：" + Thread.currentThread().getName());
    }
}
