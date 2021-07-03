package com.cxr.other.permissionDemo.byRBAC;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.security.Permission;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 系统启动时收集全部的权限方法，同步到t_permission表
 */
@Component
public class PermissionMethodCollectionListener implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    /**
     * 这里演示通过ApplicationContextAware注入，你也可以直接使用@AutoWired
     * 这一套组合拳很有趣我们一一细说：
     * 先是实现了ApplicationContextAware，把这个上下文信息拿到 ，里面包含了打了xxx注解的方法
     * 然后实现了ApplicationListener接口 （实现这个接口的类在 完整的上下文ready 的事件完成后 会触发ContextRefreshedEvent事件）
     *
     * 因为@Component 需要初始化（setApplicationContext了）注入容器，这一系列都 setApplicationContext了,自然广播ContextRefreshedEvent事件
     * 再触发listener事件 拿到打了注解的方法
     *
     * ==
     * 插话：
     * ApplicationContextAware是属于bean的生命周期 litener是IOC容器的初始化流程 肯定是前者优先
     * ==
     */
    @Autowired
    private ApplicationContext applicationContext;

    @SneakyThrows
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //一个bean只触发一次。。
        int i = applicationContext.hashCode();
        this.applicationContext = applicationContext;
        // 遍历所有Controller
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(Controller.class);
        Collection<Object> beans = beanMap.values();
        System.out.println("1");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // 得到当前用户已有的所有权限方法
        Set<String> permissionsFromDB = new HashSet<>();

        // 遍历所有Controller
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(Controller.class);
        Collection<Object> beans = beanMap.values();
        for (Object bean : beans) {
            Class<?> controllerClazz = bean.getClass();

            // 如果Controller上有@RequiresPermission，那么所有接口都要收集(isApiMethod)，否则只收集打了@Permission的接口(hasPermissionAnnotation)
            //这不断言型接口吗
            Predicate<Method> filter = AnnotationUtils.findAnnotation(controllerClazz, RequiresPermission.class) != null
                    ? this::isApiMethod
                    : this::hasPermissionAnnotation;

            // 过滤出Controller中需要权限验证的method
            Set<String> permissionMethodsWithinController = getPermissionMethodsWithinController(
                    controllerClazz.getName(),
                    controllerClazz.getMethods(),
                    filter
            );

            for (String permissionMethodInMemory : permissionMethodsWithinController) {
                // 如果是新增的权限方法 permissionsFromDB里面没有
                if (!permissionsFromDB.contains(permissionMethodInMemory)) {
                    //permissionsMapper.insert(new Permiss());
                }
            }
        }

    }

    private Set<String> getPermissionMethodsWithinController(String controllerName, Method[] methods, Predicate<Method> filter) {

        return Arrays.stream(methods)
                //点睛之笔 传入的也就是  : this::isApiMethod
                //                    : this::hasPermissionAnnotation
                .filter(filter)
                .map(method -> {
                    StringBuilder sb = new StringBuilder();
                    String methodName = method.getName();
                    return sb.append(controllerName).append("#").append(methodName).toString();
                })
                .collect(Collectors.toSet());
    }

    private boolean hasPermissionAnnotation(Method method) {
        return AnnotationUtils.findAnnotation(method, RequestMapping.class) != null
                && AnnotationUtils.findAnnotation(method, RequiresPermission.class) != null;
    }

    private boolean isApiMethod(Method method) {
        return AnnotationUtils.findAnnotation(method, RequestMapping.class) != null;
    }
}
