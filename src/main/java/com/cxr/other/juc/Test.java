package com.cxr.other.juc;

public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new MyThread(0, "A").start();
            new MyThread(1, "B").start();
            new MyThread(2, "C").start();
        }
    }
}

class MyThread extends Thread {
    private static int currentCount; // 线程共有，判断所有的打印状态
    private static Object obj = new Object(); // 线程锁对象
    private int flag; // 0：打印A；1：打印B；2：打印C
    private String value;

    public MyThread(int flag, String value) {
        this.flag = flag;
        this.value = value;
    }

    @Override
    public void run() {
        /**
         * A来了 没进去while 打印输出 唤醒
         * C来了 进入while wait()
         * B来了 没进去while 打印输出 唤醒
         * A来了 wait
         * C来了 打印输出
         */
        synchronized (obj) {
            while (currentCount % 3 != flag) {
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print(value); // 执行到这里，表明满足条件，打印
            currentCount++;
            obj.notifyAll(); // 调用notifyAll方法
        }
    }
}
