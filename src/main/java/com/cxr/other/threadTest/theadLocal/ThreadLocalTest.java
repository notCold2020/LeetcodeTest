package com.cxr.other.threadTest.theadLocal;

public class ThreadLocalTest {
    //共享变量
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 多个线程访问共享资源 并发问题
     * 如果下面sleep一会 的确能解决 那是因为sleep让Thread 1s 后才start
     */
    public static void main(String[] args) throws InterruptedException {
        ThreadLocalTest demo01 = new ThreadLocalTest();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                demo01.setContent(Thread.currentThread().getName() + "的数据");
                System.out.println("------");
                System.out.println(Thread.currentThread().getName() + "-->" + demo01.getContent());
            });
            thread.setName("thread" + i);
            thread.start();
//            Thread.sleep(1000);
        }
    }
}

class ThreadLocalTest02 {
    /**
     * 把不同线程的数据存在不同的ThreadLocalMap里面
     * A线程存在A Map里面
     * B线程存在B Map里面
     * 彼此之间存取不受影响  这样就保证了线程安全
     */
    static ThreadLocal<String> threadLocal = new ThreadLocal<String>() {
        /**
         * 为ThreadLocal赋初值；
         * 如果set方法没有被调用，ThreadLocalMap未被创建，此时使用threadLocal.get()方法得到的值为初始值，
         * 即initialValue返回的数值
         * 不重写就返回个null 容易空指针
         */
        @Override
        protected String initialValue() {
            System.out.println("init被执行了");
            return "";
        }
    };

    public String getContent() {
        return threadLocal.get();
    }

    public void setContent(String content) {
        //变量绑定到当前线程
        threadLocal.set(content);
    }

    /**
     * 多个线程访问共享资源 并发问题
     * 如果下面sleep一会 的确能解决 那是因为sleep让Thread 1s 后才start
     */
    public static void main(String[] args) throws InterruptedException {
        try {
            ThreadLocalTest02 demo02 = new ThreadLocalTest02();
//            demo02.getContent();
            for (int i = 0; i < 5; i++) {
                Thread thread = new Thread(() -> {
//                    try {
////                        Thread.sleep(1000);
//                        System.out.println("我睡了");
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    demo02.setContent(Thread.currentThread().getName() + "的数据");
                    System.out.println("------");
                    System.out.println(Thread.currentThread().getName() + "-->" + demo02.getContent());
                });
                thread.setName("thread" + i);
                thread.start();
//            Thread.sleep(1000);
            }

//            Thread.sleep(2000);
        } finally {
            threadLocal.remove();
            System.out.println("finally");
        }
    }
}
