package com.cxr.other.spring.profileDemo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: CiXingrui
 * @Create: 2021/10/12 7:38 下午
 *
 *
 * https://onestar.blog.csdn.net/article/details/114915592
 *
 * 也可以通过修改pom.xml文件，然后在maven中配置，不同环境对应着不同路径下的配置文件。
 * 直接maven打包的时候 打的就是不同环境的包
 *
 */
public class ProfileDemoTest {
    public static void main(String[] args) {
        //可以像下面这么写 也可以加上VM启动参数：-Dspring.profiles.active=dev，
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.getEnvironment().setActiveProfiles("dev");
        applicationContext.register(AppConfig.class);
        applicationContext.refresh();

        /**
         * 上面的写法只不过就是把打开了new AnnotationConfigApplicationContext(AppConfig.class)；
         * 在刷新容器之前把环境变量设置进去
         *
         * 		this();
         * 		register(componentClasses);
         * 		refresh();
         */

        ProfileTest bean = applicationContext.getBean(ProfileTest.class);
    }
}
