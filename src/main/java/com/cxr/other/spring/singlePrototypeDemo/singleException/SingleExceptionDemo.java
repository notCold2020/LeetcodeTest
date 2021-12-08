package com.cxr.other.spring.singlePrototypeDemo.singleException;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author: CiXingrui
 * @Create: 2021/10/12 10:50 上午
 */
@Component
@Data
public class SingleExceptionDemo {
    int i;

    protected void test() {
        System.out.println(i++);
    }
}
