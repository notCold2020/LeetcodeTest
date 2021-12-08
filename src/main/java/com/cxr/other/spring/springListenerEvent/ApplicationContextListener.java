package com.cxr.other.spring.springListenerEvent;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @Author: CiXingrui
 * @Create: 2021/8/2 7:47 下午
 *
 * 测试两者执行先后顺序
 * 自定义事件 extends ApplicationEvent
 */
@Component
public class ApplicationContextListener  implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    /**
     * 肯定是这个在前面啊！
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("---setApplicationContext--");
    }


    /**
     * ContextRefreshedEvent会在IOC容器初始化结束后广播一个事件，这个时候
     * IOC容器初始化完毕,必然是在setApplicationContext()之后的
     *
     * 广播contextRefreshedEvent事件会触发下面的onApplicationEvent接口
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //这样也可以拿到ApplicationContext
        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        System.out.println("--contextRefreshedEvent--");
    }
}
