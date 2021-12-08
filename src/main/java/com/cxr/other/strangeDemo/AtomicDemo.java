package com.cxr.other.strangeDemo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: CiXingrui
 * @Create: 2021/11/13 1:30 下午
 */
public class AtomicDemo {

    public static void main(String[] args) {
        testAtomic();
    }

    /**
     * 原理其实就是CAS
     * 保证了只有
     */
    private static void testAtomic() {
        AtomicInteger atomicInteger = new AtomicInteger();
        //get 然后再+1
        atomicInteger.getAndIncrement();
        // 拿到+1之后的值
        atomicInteger.incrementAndGet();
        //get然后+n
        atomicInteger.getAndAdd(11);
        atomicInteger.addAndGet(11);
        //-1
        atomicInteger.decrementAndGet();

    }
}
