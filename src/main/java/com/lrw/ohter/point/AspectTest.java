package com.lrw.ohter.point;

import com.lrw.shiro.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AspectTest {
    static Logger logger = LoggerFactory.getLogger(AspectTest.class);

    public static void main(String[] args) {
        beforeTest();
    }

    static void beforeTest() {
        logger.info("执行操作");
    }
}
