package com.cxr.other.strangeDemo.lazyDemo;

import java.util.function.Supplier;

/**
 * 为了方便与标准的 Java 函数式接口交互，Lazy 也实现了 Supplier
 */
public class Lazy<T> implements Supplier<T> {

    private final Supplier<? extends T> supplier;

    // 利用 value 属性缓存 supplier 计算后的值
    private T value;

    private Lazy(Supplier<? extends T> supplier) {
        this.supplier = supplier;
    }

    public static <T> Lazy<T> of(Supplier<? extends T> supplier) {
        return new Lazy<>(supplier);
    }

    public T get() {
        //拿的时候先拿缓存
        if (value == null) {
            //延迟加载的方法
            T newValue = supplier.get();

            if (newValue == null) {
                throw new IllegalStateException("Lazy value can not be null!");
            }
            value = newValue;
        }

        return value;
    }
}
