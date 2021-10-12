package com.cxr.other.demo.controller;

import com.cxr.other.demo.dao.testInter;
import com.cxr.other.demo.entriy.User;
import com.cxr.other.demo.service.UserService;
import com.cxr.other.spring.aspect.AspectTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
public class UserController implements ApplicationContextAware {
    @Autowired
    private UserService userService;

    @Autowired
    private AspectTest aspectTest;

    @Autowired
    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //一个bean只触发一次。。
        this.applicationContext = applicationContext;
        int i = applicationContext.hashCode();
        // 遍历所有Controller
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(Controller.class);
        Collection<Object> beans = beanMap.values();
        System.out.println();
    }

    Logger logger = LoggerFactory.getLogger(UserController.class);

    private testInter testInter;

    @Autowired
    @Transactional
    public void setTestInter(@Qualifier("testInterImpl1") com.cxr.other.demo.dao.testInter testInter) {
        testInter.testInter();
        this.testInter = testInter;
    }

    @RequestMapping(value = {
            "/page",
            "page*",
            "view/*,**/msg"
    })
    //@RequestBody 用来接收json数据
    // 如果用对象来接收并且和对象中的变量对应上 那就能拿到这个对象
    //如果用String 来接收那就能拿到个json的String
    @ResponseBody
    private Integer getUser(@RequestParam(defaultValue = "15", required = false, value = "one") Integer one) {
//        User user1 = new User(user.getId(),user.getPwdddd(),user.getUserName());
        System.out.println(one.getClass().toString());
        logger.info("dddddd");
        return one;
    }

    @RequestMapping("/test/test/001")
    private Object testUser(@RequestBody User user) {
//        user.setDate(new Date());
        return user;
    }


    /**
     * 1.AOP都是代理帮我们实现的，所以aspectTest这个东西得是代理才行
     * 2.别忘了在启动类开启@Enablexxxxxx(xxxx=true)
     */
    @RequestMapping("/test/respect")
    private String aspectTest() {
        System.out.println("--");
        return aspectTest.beforeTest();
    }


}
