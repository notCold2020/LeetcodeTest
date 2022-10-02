package com.cxr.other.spring.lazyDemo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: CiXingrui
 * @Create: 2021/9/15 7:34 下午
 */
public class lazyTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(UserDao.class);
        /**
         * 现象：下面bean里面的"属性"由于是懒加载的，依赖注入的时候注入进来的是代理对象
         *
         * 1.@Lazy被标记在类上 但是在@autowire的时候 把@Lazy打了标记的类注入进来了 全局变量上没有@Lazy 这样懒加载是失效的
         * 你明明想用懒加载 但是你又在bean里面用人家 那人家不得乖乖加载吗
         * @Lazy的原则就是 只要用到了 就会实例化 不用到了就是懒加载
         *
         * 2.如果bean里面某个字段标记了@Lazy，类上没有，那么初始化这个bean的时候会为这个字段创建一个代理，只是一个代理对象，没有实例化bean
         *   就变成了A里面依赖代理对象B
         *
         * 3.如果@lazy打在类上，那就项目启动的时候不会实例化(可以在构造方法中打印xxx看一下)，可以简单理解为 跳过了，getBean的时候再说
         *
         * 4.所以懒加载解决循环依赖的原因的确是让beanA和代理B依赖
         *
         * 5.如果类A上是普通的@service 但是在B类中注入A并且在A字段上标记@Lazy，那么ioc容器中还是会有A，但是B类中的A是Cglib代理过的代理对象
         *
         * 6.如果一个类上有@Component @Lazy，并且没有其他的类哟；依赖注入它/虽然依赖注入但是有@Lazy，那么这个类就不会被放进ioc容器，而是在
         * getBean()的时候才实例化这个bean，这里说的getBean是源码中的doGetBean()，其实applicationContext.getBean()最终也调用的是doGetBean()
         *
         *
         * 7.如果@lazy在全局变量上，那么是在依赖注入的时候判断，是不是注入进去一个代理对象
         * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#resolveDependency(org.springframework.beans.factory.config.DependencyDescriptor, java.lang.String, java.util.Set, org.springframework.beans.TypeConverter)
         *
         * 8.如果@lazy在类上，就先不加载，跳过，没找到具体在哪，可能是加载配置类的部分？？
         *
         * https://xiejun.blog.csdn.net/article/details/103267125
         */
        UserService bean = applicationContext.getBean(UserService.class);
        UserDao bean2 = applicationContext.getBean(UserDao.class);
    }
}
