package com.cxr.other.spring.springMyselfDemo;

/**
 * @Date 2022/5/21 3:33 下午
 * @Created by CiXingrui
 */
public class Test {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        CixingruiApplicationContext context = new CixingruiApplicationContext(AppConfig.class);
        DomainIface domainServiceName = (DomainIface) context.getBean("DomainService");
        domainServiceName.test();
    }


}
