package com.cxr.other.spring.springMyselfDemo;


import lombok.Data;

/**
 * @Date 2022/5/21 5:45 下午
 * @Created by CiXingrui
 */
@CixingruiCompomnet("DomainService")
@Data
public class DomainService implements CixingruiBeanNameAware,CixingruiInitializingBean , DomainIface {

    @CixingruiAutoWire
    private OrderService OrderService;

    private String beanName;

    private String beanPostProcessorName;



    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("==执行初始化==");
    }

    @Override
    public void test() {
        System.out.println("AOP测试方法");

    }
}
