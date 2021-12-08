package com.cxr.other.spring.singlePrototypeDemo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: CiXingrui
 * @Create: 2021/9/15 4:18 下午
 */
@Service("UserServiceTest")
@Scope("prototype")
public class UserService {

    @Resource
    private UserDao userDao;
}
