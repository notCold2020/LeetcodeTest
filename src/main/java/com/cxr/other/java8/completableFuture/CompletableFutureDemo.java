package com.cxr.other.java8.completableFuture;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * https://www.jianshu.com/p/6bac52527ca4
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws Exception {
        completableFutureAllOf();
    }

    //无返回值,异步的但是会被get()阻塞
    public static void runAsync() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
            }
            System.out.println("run end ...");
        });

        Void aVoid = future.get();
        System.out.println("返回值：" + aVoid);//null
    }

    //有返回值
    public static void supplyAsync() throws Exception {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
            }
            System.out.println("run end ...");
            return "supplyAsync()返回值";
        });

        String res = future.get();
        System.out.println("返回值：" + res);
    }

    /**
     *  当用runAsync()的时候没办法对异常进行处理，所以有了exceptionally()
     */
    public static void whenComplete() throws Exception {
        //直接在CompletableFuture内部捕获异常 是捕获不到的
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                if (new Random().nextInt() % 2 >= 0) {
                    int i = 12 / 0;
                    System.out.println("异常捕获1");//如果抛出了异常这句不执行
                }
            } catch (InterruptedException e) {
                System.out.println("异常捕获2");//如果抛出了异常这句不执行
            }
            System.out.println("run end ...");//如果抛出了异常这句不执行
        });
        System.out.println("主线程开始:"+Thread.currentThread().getName());
        /**
         * 当完成的时候会执行这个方法
         * 方法最下面TimeUnit.SECONDS.sleep(2) 是为了让主线程 晚点结束
         * 不然控制台都没了 这个"执行完成！"会打印到哪去呢
         *
         * 注意：这个whenComplete也算在新开启的线程,即和exceptionally的线程也就是
         * CompletableFuture 帮我们开启的线程
         * 所以future.xxxx()的方法始终都是异步的，主线程该干嘛干嘛
         */
        future.whenComplete(new BiConsumer<Void, Throwable>() {
            @SneakyThrows
            @Override
            public void accept(Void t, Throwable action) {
                TimeUnit.SECONDS.sleep(40);
                System.out.println("执行完成！"+"线程："+Thread.currentThread().getName());
            }
        });

        /**
         * 异常的时候调用
         * whenComplete依旧会执行 在exceptionally后
         */
        future.exceptionally(new Function<Throwable, Void>() {
            @SneakyThrows
            @Override
            public Void apply(Throwable t) {
                System.out.println("执行失败！" + t.getMessage()+":线程:"+Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(2);
                return null;
            }
        });

        TimeUnit.SECONDS.sleep(4);
        System.out.println("主线程执行完成！:"+Thread.currentThread().getName());
    }

    /**
     * 当一个线程依赖另一个线程时，可以使用 thenApply 方法来把这两个线程串行化。
     * 同样的，future执行的thenApply supplyAsync什么的都是新开了一个线程 是异步的 独立于主线程
     */
    private static void thenApply() throws Exception {
        CompletableFuture<Long> future = CompletableFuture.supplyAsync(new Supplier<Long>() {
            @Override
            public Long get() {
                long result = new Random().nextInt(100);
                System.out.println("随机数result1=" + result);
                return result;
            }
        }).thenApply(new Function<Long, Long>() {
            @SneakyThrows
            @Override
            public Long apply(Long t) {
                long result = t * 5;
                TimeUnit.SECONDS.sleep(5);
                System.out.println("5*随机数result2=" + result);
                return result;
            }
        });

//        long result = future.get();//阻塞的获取
        System.out.println("结果：result3：" + "result");
    }

    /**
     * 和thenApply()相比 新增异常处理
     */
    public static void handle() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(new Supplier<Integer>() {

            @Override
            public Integer get() {
                int i = 10 / 0;
                return new Random().nextInt(10);
            }

        }).handle(new BiFunction<Integer, Throwable, Integer>() {
            @Override
            public Integer apply(Integer param, Throwable throwable) {
                int result = -1;
                if (throwable == null) {
                    result = param * 2;
                } else {
                    System.out.println(throwable.getMessage());
                }
                return result;
            }
        });

        System.out.println(future.get());
    }

    //接收任务的处理结果，并消费处理，无返回结果。
    public static void thenAccept() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                return new Random().nextInt(10);
            }
        }).thenAccept(integer -> {
            //可以在这里执行一些方法 比如把数据放到map/list里面
            System.out.println(integer);
        });
        Void aVoid = future.get();
    }

    /**
     * 场景： 我现在要获取4个mapper调用的方法的数据存到一个bean中
     *
     * CompletableFuture.supplyAsync() x 4,也不需要返回值 直接在thenAccept方法里面放到map/list就行
     * 括号 ： List<CompletableFuture<Void>> futureList = Lists.newArrayList();
     * 最终执行 CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0])).join();阻塞结果 返回上文的map/list
     *
     * ps : toArray()如果里面不指定会返回一个Object的数组 咱们相当于指定了一下
     */
    private static void completableFutureAllOf() throws Exception {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(8);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "f1";
        });

        /**
         * whenComplete 和 whenCompleteAsync 的区别：
         * whenComplete：是执行当前任务的线程执行继续执行 whenComplete 的任务。
         * whenCompleteAsync：是执行把 whenCompleteAsync 这个任务继续提交给线程池来进行执行。
         * 反正都不是主线程
         */
        f1.whenCompleteAsync(new BiConsumer<String, Throwable>() {
            @SneakyThrows
            @Override
            public void accept(String s, Throwable throwable) {
                System.out.println(System.currentTimeMillis() + ":" + s);
                TimeUnit.SECONDS.sleep(2);
            }
        });

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "f2";
        });

        f2.whenCompleteAsync(new BiConsumer<String, Throwable>() {
            @SneakyThrows
            @Override
            public void accept(String s, Throwable throwable) {
                System.out.println(System.currentTimeMillis() + ":" + s);
                TimeUnit.SECONDS.sleep(2);
            }
        });

        CompletableFuture<Void> all = CompletableFuture.allOf(f1, f2);

        System.out.println(System.currentTimeMillis() + ":阻塞");
        /**
         * 阻塞，直到所有任务结束。
         * 注意当前时间毫秒值 打印顺序可能有点问题
         */
        all.join();
        System.out.println(System.currentTimeMillis() + ":阻塞结束");

        TimeUnit.SECONDS.sleep(1);

        //一个需要耗时8秒，一个需要耗时15秒，只有当最长的耗时15秒的完成后，才会结束。因为是allof()，如果是anyof那就有一个结束就可以
        System.out.println("任务均已完成。");

    }

}
