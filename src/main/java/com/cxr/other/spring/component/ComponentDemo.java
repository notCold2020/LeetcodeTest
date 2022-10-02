package com.cxr.other.spring.component;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Date 2022/5/17 3:47 下午
 * @Created by CiXingrui
 */
@Component
public class ComponentDemo {


    /**
     * 1.includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Cixingrui.class)
     * includeFilters其实就是个list，代表要加载到ioc容器里面的bean,满足这个条件 就放到ioc容器中
     * 比如上面说的是 如果一个类上有@Cixingrui 那就吧这个类放进ioc容器中
     *
     * 2.excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Cixingrui.class)
     * 这个是他的好兄弟，代表如果一个类上有@Cixingrui 那么就算类上有@Component 也不把他放进ioc容器中，优先级更高
     *
     * 3.excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
     *              value = {MyBeanPostProcessorImpl.class,MyBeanPostProcessorImpl.class}))
     * 这个 FilterType.ASSIGNABLE_TYPE 代表的是根据类来过滤,可以指定多个
     *
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ComponentConfig.class);
        BeanSelf bean = applicationContext.getBean(BeanSelf.class);
//        ComponentConfig bean1 = applicationContext.getBean(ComponentConfig.class);
    }

}
