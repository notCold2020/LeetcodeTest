package com.cxr.other.spring.singlePrototypeDemo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: CiXingrui
 * @Create: 2021/9/15 4:36 下午
 */
public class SinglePrototypeTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(UserDao.class);

        /**
         * 其实@Autowire/@Resource也是要调用getBean方法，这时候就已经判断单例还是原型,然后反射注入进Bean里面了。
         * 而且UserService也是有单例/原型的 看地址就能看到
         *
         * 所以现象就是：UserService里面的属性，地址不一样，UserService本身的地址也不一样（全是原型模式的情况）
         * 上面的全是原型指的是@Autowire注入进来的和当前的类都是原型的，不然只有注入进来的bean是原型的，
         * 项目启动加载A类，A类注入B类 放进缓存
         * getBean(A),发现缓存里面有，直接拿了,没有再次实例化B这个操作了
         */
        UserService bean = applicationContext.getBean(UserService.class);
        UserService bean2 = applicationContext.getBean(UserService.class);
    }
}
