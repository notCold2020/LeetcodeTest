package com.cxr.other.spring.factoryBeanDemo;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;


/**
 * daoFactoryBean：得到是当前产生的对象
 * &daoFactoryBean：得到的是当前对象
 */
@Component("daoFactoryBean")
public class DaoFactoryBean implements FactoryBean<TempFactoryBean> {
    /*
     * 实现了factoryBean接口
     * spring中会存在两个bean
     * 一个是当前对象（&+当前类的名字），
     * 一个是getObject返回值  `
     **/

    @Override
    public boolean isSingleton() {
        return true;
    }

    //返回的一个bean
    @Override
    public TempFactoryBean getObject() throws Exception {
        //这里可以进行很多业务逻辑处理
        TempFactoryBean tempFactoryBean = new TempFactoryBean();
        tempFactoryBean.setDesc("getObject()方式导入bean");
        return tempFactoryBean;
    }

    @Override
    public Class<?> getObjectType() {
        return TempFactoryBean.class;
    }
}
