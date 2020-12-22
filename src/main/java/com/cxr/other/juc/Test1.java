package com.cxr.other.juc;

class PrintABCBy30 implements Runnable {
    private static int count = 1;

    @Override
    public void run() {
        /**
         * 3个线程过来了 其实是没有顺序的 但不管哪个线程来了 我们的目的其实是
         * 让打印A B C
         * A来了 打印出了A
         * C来了 打印出了B
         * C来了 打印出了C 。。。
         */
        synchronized ("abc") {
            while (count < 30) {
                switch (count % 3) {
                    case 1:
                        System.out.printf("A");
                        break;
                    case 2:
                        System.out.printf("B");
                        break;
                    case 0:
                        System.out.printf("C");
                        break;
                }
                count++;
            }
        }
    }
}

public class Test1 {
    public static void main(String[] args) {
        Thread t1 = new Thread(new PrintABCBy30());
        Thread t2 = new Thread(new PrintABCBy30());
        Thread t3 = new Thread(new PrintABCBy30());
        t1.start();
        t2.start();
        t3.start();
    }
}

