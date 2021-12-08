package com.cxr.other.spring.lazyDemo;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: CiXingrui
 * @Create: 2021/9/15 4:18 下午
 */
@Service("UserServiceLazy")
public class UserService {

    /**
     * UserDao是@Lazy的，所以实例化的时候发现有@Lazy,就创建了一个代理对象。
     * 依赖注入的时候，就把代理对象注入进啦里
     */
    @Resource
    @Lazy
    private UserDao userDao;
}
