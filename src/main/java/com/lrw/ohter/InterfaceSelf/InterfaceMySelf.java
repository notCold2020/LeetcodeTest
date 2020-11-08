package com.lrw.ohter.InterfaceSelf;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.METHOD)//注解作用于方法
public @interface InterfaceMySelf {
    String name() default "张三丰";

    int abilityCount() default 2;

    String[] abilityNames() default {"太极拳", "太极剑"};
}
