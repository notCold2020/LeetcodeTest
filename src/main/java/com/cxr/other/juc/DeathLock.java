package com.cxr.other.juc;

public class DeathLock {
    public static void main(String[] args) {
        new Thread(() -> {
            synchronized ("A") {
                System.out.println(Thread.currentThread().getName() + "持有A锁");
                synchronized ("B") {
                    System.out.println(Thread.currentThread().getName() + "持有B锁");
                }
            }
        }, "A").start();
        /**
         * 1.jps -l 查看进程信息
         * 2.jstack 进程号 查看堆栈信息
         * 3.看报错信息 有一句是
         * ===================================================
         * "B":
         *         at com.cxr.other.juc.DeathLock.lambda$main$1(DeathLock.java:18)
         *         - waiting to lock <0x000000076cad7c48> (a java.lang.String)
         *         - locked <0x000000076cad99f0> (a java.lang.String)
         *         at com.cxr.other.juc.DeathLock$$Lambda$2/670576685.run(Unknown Source)
         *         at java.lang.Thread.run(Thread.java:748)
         * "A":
         *         at com.cxr.other.juc.DeathLock.lambda$main$0(DeathLock.java:9)
         *         - waiting to lock <0x000000076cad99f0> (a java.lang.String)
         *         - locked <0x000000076cad7c48> (a java.lang.String)
         *         at com.cxr.other.juc.DeathLock$$Lambda$1/758705033.run(Unknown Source)
         *         at java.lang.Thread.run(Thread.java:748)
         *
         * todo:Found 1 deadlock.
         */
        new Thread(() -> {
            synchronized ("B") {
                System.out.println(Thread.currentThread().getName() + "持有B锁");
                synchronized ("A") {
                    System.out.println(Thread.currentThread().getName() + "持有A锁");
                }
            }
        }, "B").start();
    }
}
