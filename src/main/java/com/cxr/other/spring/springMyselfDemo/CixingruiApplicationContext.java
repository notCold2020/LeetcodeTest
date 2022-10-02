package com.cxr.other.spring.springMyselfDemo;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Date 2022/5/21 3:27 下午
 * @Created by CiXingrui
 */
public class CixingruiApplicationContext {

    private Class configClass;

    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, CixingruiBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    private List<CixingruiBeanPostProcessor> beanPostProcessorList = new ArrayList<>();


    public CixingruiApplicationContext(Class configClass) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.configClass = configClass;
        /**
         * 解析配置类
         * 1.@CompomentScan
         * 2.扫描路径 填充beanDefinitionMap
         */
        scanBean(configClass);

        for (String beanName : beanDefinitionMap.keySet()) {

            CixingruiBeanDefinition beanDefinition = beanDefinitionMap.get(beanName);

            if (beanDefinition.getScope().equals("singleton")) {
                //单例bean - 创建对象
                Object bean = createBean(beanDefinition);
                //放进单例池
                singletonObjects.put(beanName, bean);
            }
        }

    }

    private Object createBean(CixingruiBeanDefinition beanDefinition) throws IllegalAccessException, InstantiationException {

        Class clazz = beanDefinition.getClazz();
        Object instance = clazz.newInstance();

        //依赖注入 拿到所有属性
        for (Field declaredField : clazz.getDeclaredFields()) {

            if (declaredField.isAnnotationPresent(CixingruiAutoWire.class)) {

                Object bean = getBean(declaredField.getName());

                if (bean == null) {
                    bean = createBean(beanDefinitionMap.get(declaredField.getName()));
                }

                declaredField.setAccessible(true);
                declaredField.set(instance, bean);
            }

        }

        //依赖注入之后，开始注入Aware相关属性
        if (instance instanceof CixingruiBeanNameAware) {
            ((CixingruiBeanNameAware) instance).setBeanName(clazz.getSimpleName());
        }

        for (CixingruiBeanPostProcessor beanPostProcessor : beanPostProcessorList) {
            instance = beanPostProcessor.postProcessBeforeInitialization(instance, clazz.getSimpleName());
        }


        //初始化 @InitializingBean
        if (instance instanceof CixingruiInitializingBean) {
            ((CixingruiInitializingBean) instance).afterPropertiesSet();
        }

        for (CixingruiBeanPostProcessor beanPostProcessor : beanPostProcessorList) {
            instance = beanPostProcessor.postProcessAfterInitialization(instance, clazz.getSimpleName());
        }



        return instance;
    }

    private void scanBean(Class configClass) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //解析配置类
        CixingruiComponentScan declaredAnnotation = (CixingruiComponentScan) configClass.getDeclaredAnnotation(CixingruiComponentScan.class);

        //拿到注解的value值
        String path = declaredAnnotation.value();

        ClassLoader classLoader = CixingruiApplicationContext.class.getClassLoader();

        URL resource = classLoader.getResource("com/cxr/other/spring/springMyselfDemo");

        File file = new File(resource.getFile());
        if (file.isDirectory()) {
            File[] files = file.listFiles();

            for (File file1 : files) {
                String absolutePath = file1.getAbsolutePath();
                String className = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class")).replace("/", ".");

                //获得这个类的class对象
                Class<?> aClass = classLoader.loadClass(className);

                //类上有CixingruiCompomnet注解
                if (aClass.isAnnotationPresent(CixingruiCompomnet.class)) {

                    CixingruiBeanDefinition beanDefinition = new CixingruiBeanDefinition();
                    beanDefinition.setClazz(aClass);

                    //当前类实现了CixingruiBeanPostProcessor接口 【instance of是判断实例的，现在这个还没实例化，还是个class对象】
                    if (CixingruiBeanPostProcessor.class.isAssignableFrom(aClass)) {
                        CixingruiBeanPostProcessor bean = (CixingruiBeanPostProcessor) createBean(beanDefinition);
                        beanPostProcessorList.add(bean);
                    }


                    CixingruiCompomnet declaredAnnotation1 = aClass.getDeclaredAnnotation(CixingruiCompomnet.class);
                    String beanName = declaredAnnotation1.value();


                    //判断单例还是原型
                    if (aClass.isAnnotationPresent(CixingruiScope.class)) {
                        CixingruiScope declaredAnnotation2 = aClass.getDeclaredAnnotation(CixingruiScope.class);
                        beanDefinition.setScope(declaredAnnotation2.value());
                    } else {
                        //默认单例
                        beanDefinition.setScope("singleton");
                    }

                    //放进beanDefine缓存
                    beanDefinitionMap.put(beanName, beanDefinition);


                }


            }
        }
    }


    public Object getBean(String beanName) throws InstantiationException, IllegalAccessException {

        if (beanDefinitionMap.containsKey(beanName)) {

            CixingruiBeanDefinition beanDefinition = beanDefinitionMap.get(beanName);

            if (beanDefinition.getScope().equals("singleton")) {
                return singletonObjects.get(beanName);
            } else {
                //原型bean,创建bean返回
                return createBean(beanDefinition);
            }


        } else {
            return null;
        }

    }
}
