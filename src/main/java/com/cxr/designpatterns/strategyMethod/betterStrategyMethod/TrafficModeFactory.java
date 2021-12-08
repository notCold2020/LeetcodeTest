package com.cxr.designpatterns.strategyMethod.betterStrategyMethod;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 交通方式工厂类
 */
@Component
public class TrafficModeFactory implements ApplicationContextAware {


    private static Map<TrafficCode, TrafficMode> trafficBeanMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        /**
         * key：接口实现类 首字母小写
         * value：接口实现类
         */
        Map<String, TrafficMode> map = applicationContext.getBeansOfType(TrafficMode.class);
        /**
         * 这个foreach可以循环key 和 value
         * 不就是语法糖吗
         */
        map.forEach((key, value) -> trafficBeanMap.put(value.getCode(), value));
    }

    public static <T extends TrafficMode> T getTrafficMode(TrafficCode code) {
        return (T) trafficBeanMap.get(code);
    }


}
