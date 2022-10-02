package com.cxr.other.java8.completableFuture;

import cn.hutool.core.collection.ListUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * https://www.jianshu.com/p/6bac52527ca4
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws Exception {
        whenComplete();
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

    //有返回值，异步的，最常用，依旧会被get()阻塞
    public static void supplyAsync() throws Exception {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
            }
            System.out.println("run end ...");
            return "supplyAsync()返回值";
        });

        String res = future.get();
        System.out.println("返回值：" + res);
    }

    /**
     * 如果我们希望当异步的方法执行完之后 执行特定操作 可以用whenComplete
     * 当用runAsync()的时候没办法对异常进行处理(因为没有返回值),所以有了exceptionally(),当有异常的时候执行exceptionally
     * 普通的方法就直接执行可以用whenComplete
     * 要注意的是所有future.方法()的这种方法都是异步的
     * 具体用的哪个线程 还不清楚
     * <p>
     * 而且whenComplete由于也是异步的，所以下面的future.join是拦不住whenComplete的
     * 也就是说 runAsync执行之后，join()阻塞就过去了，这样会造成我们whenComplete里面的逻辑可能拿不到值
     * 解决方案：whenComplete也会返回一个future,阻塞这个whenComplete返回的future即可
     */
    public static void whenComplete() throws Exception {
        //直接在CompletableFuture内部捕获异常 是捕获不到的
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("执行开始！" + "线程：" + Thread.currentThread().getName());
                if (new Random().nextInt() % 2 >= 0) {
                    int i = 12 / 0;
                    System.out.println("异常捕获1");//如果抛出了异常这句也不执行
                }
            } catch (InterruptedException e) {
                System.out.println("异常捕获2");//如果抛出了异常这句也不执行
            }
            System.out.println("run end ...");//如果抛出了异常这句也不执行
        });
        System.out.println("主线程开始:" + Thread.currentThread().getName());
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
                TimeUnit.SECONDS.sleep(2);
                System.out.println("执行完成！" + "线程：" + Thread.currentThread().getName());
            }
        });

        System.out.println("判断是不是future.方法()这种都是异步的");

        /**
         * 异常的时候调用
         * whenComplete依旧会执行 在exceptionally后
         */
        future.exceptionally(new Function<Throwable, Void>() {
            @SneakyThrows
            @Override
            public Void apply(Throwable t) {
                System.out.println("执行失败！" + t.getMessage() + ":线程:" + Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(2);
                return null;
            }
        });

        future.join();//join方法会抛出异常
        TimeUnit.SECONDS.sleep(4);
        System.out.println("主线程执行完成！:" + Thread.currentThread().getName());
    }

    /**
     * 当一个线程依赖另一个线程时，可以使用 thenApply 方法来把这两个线程串行化。
     * <p>
     * 还是那句话future.方法()都是异步的，所以下面的supplyAsync、thenApply是一体的
     * thenApply肯定是要阻塞的等待supplyAsync()
     * 而get()方法也是阻塞的，阻塞的等待上面这一坨
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

        long result = future.get();//阻塞的获取
        System.out.println("结果：result3：" + "result");
    }

    /**
     * 和thenApply()相比 新增异常处理
     * 我们可以在handle方法里面判断 如果出现异常了，我们做一些xxxx处理
     * 而thenApply如果被依赖的线程出异常了，则不执行thenApply()方法
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

        System.out.println(future.get());//get自然还是阻塞的
    }

    /**
     * 和thenApply一样的，也是对前者返回结果集处理，但是没有返回值
     * 并且前面这一坨supplyAsync+thenAccept依旧是异步的
     * 但是这一坨里面的thenAccept是阻塞等待supplyAsync的
     */
    public static void thenAccept() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @SneakyThrows
            @Override
            public Integer get() {

                TimeUnit.SECONDS.sleep(2);
                return new Random().nextInt(10);
            }
        }).thenAccept(integer -> {
            //可以在这里执行一些方法 比如把数据放到map/list里面
            System.out.println("thenAccept开始执行");
            System.out.println(integer);

        });
        Void aVoid = future.get();
//        TimeUnit.SECONDS.sleep(5);
        System.out.println("执行完成");
    }

    /**
     * 场景： 我现在要获取4个mapper调用的方法的数据存到一个bean中
     * <p>
     * CompletableFuture.supplyAsync() x 4,也不需要返回值 直接在thenAccept方法里面放到map/list就行
     * 括号 ： List<CompletableFuture<Void>> futureList = Lists.newArrayList();
     * 最终执行 CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0])).join();阻塞结果 返回上文的map/list
     * <p>
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
         * 反正都不是主线程 因为他们都是异步的
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

        //我感觉allOf方法就是把多个future拼到一起了，这样
        CompletableFuture<Void> all = CompletableFuture.allOf(f1, f2);
//        CompletableFuture<Void> any = CompletableFuture.allOf(f1, f2);

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

    /**
     * 场景：现在要填充一个List<bean>，bean里面的A属性是在A数据库，B属性是在B数据库
     * 问题点：如果是以前可能就是用list抽取出ids的集合然后查询出来一个A的map和一个B的map,然后拼接的时候从map里面取
     * 解决方案：现在直接异步地读
     * 提升：比如A读出来map需要5s，B需要5s，现在只需要5s
     * --------------------------------------------------
     */
    public static void demo1() {
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        //任务集合
        List<CompletableFuture<Void>> tasks = new ArrayList<>();
        //入参
        List<String> ids = new ArrayList<>();

        Map<String, Object> aMap = new HashMap<>();
        Map<String, Object> bMap = new HashMap<>();

        CompletableFuture<Void> a = CompletableFuture.supplyAsync(() -> {
            //模拟查询数据库，用ids集合查询出数据列表
            return dbMethod(ids);
        }, executorService).thenAccept(res -> {
            aMap.putAll(res);
        });
        tasks.add(a);

        CompletableFuture<Void> b = CompletableFuture.supplyAsync(() -> {
            //模拟查询数据库，用ids集合查询出数据列表
            return dbMethod(ids);
        }, executorService).thenAccept(res -> {
            bMap.putAll(res);
        });
        tasks.add(b);

        //阻塞，过了下面这行代码就可以开始拼接数据了
        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();

        for (String id : ids) {
            //拼接数据
            Object o1 = aMap.get(id);
            Object o2 = bMap.get(id);
        }

        long end = System.currentTimeMillis();

        System.out.println("结束,耗时：" + (end - start));

    }

    /**
     * 场景：批量查询，咱们也不知道查询多少，如果是对面传来几十条还可以，要是人家传了个1w条的 我们还给他同步的查询吗
     * 解决方案：判断，如果list太大就线程池查询
     */
    public static void demo2() {
        List<String> ids = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        if (ids.size() < 20) {
            //直接同步查询数据库
            return;
        }
        //超过20条了
        List<List<String>> partition = ListUtil.partition(ids, 20);

        for (List<String> list : partition) {
            executorService.execute(() -> {
                dbMethod(list);
            });
        }

    }

    @SneakyThrows
    private static Map<String, Object> dbMethod(List<String> s) {
        Thread.sleep(5000L);
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("s.get(0)", new Object());
        return objectObjectHashMap;
    }

    /**
     * allOf的执行顺序就按照代码的执行顺序，因为CompletableFuture本质上其实就是异步方法，也没办法给它一个顺序
     * 毕竟执行到异步方法其实就跳过去了
     * 所以就是单纯的看谁先执行到
     * <p>
     * 如果我们一定要有顺序的结果，可以在join之后用get()方法来获取值，因为已经阻塞过了，这个时候get就不会阻塞，很快就可以拿到有顺序的值了。
     * 顺序是我们自己定义的。
     */
    static void testAllofIfOrder() throws ExecutionException, InterruptedException {
        Long start = System.currentTimeMillis();

        //按照异步方法执行时间的结果集
        List<List<String>> res = new ArrayList<>();

        //自己循环的有序结果集
        List<List<String>> res2 = new ArrayList<>();

        CompletableFuture<List<String>> a = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("a执行完了");
            return Collections.singletonList("A");
        }).thenApply(r -> {
            res.add(r);
            return r;
        });

        CompletableFuture<List<String>> b = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("b执行完了");
            return Collections.singletonList("B");
        }).thenApply(r -> {
            res.add(r);
            return r;
        });
        System.out.println("异步");

        CompletableFuture<List<String>> c = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("c执行完了");
            return Collections.singletonList("C");
        }).thenApply(r -> {
            res.add(r);
            return r;
        });
        System.out.println("异步");


        CompletableFuture<List<String>> d = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("D执行完了");
            return Collections.singletonList("D");
        }).thenApply(r -> {
            res.add(r);
            return r;
        });

        System.out.println("结束异步");
        List<CompletableFuture<List<String>>> tasks = new ArrayList<>();
        tasks.add(a);
        tasks.add(b);
        tasks.add(c);
        tasks.add(d);

        CompletableFuture<List<String>>[] completableFutures = tasks.toArray(new CompletableFuture[0]);
        CompletableFuture.allOf(completableFutures).join();
        Long end1 = System.currentTimeMillis();
        System.out.println("异步方法全部阻塞完毕，耗时:" + (end1 - start));

        for (CompletableFuture<List<String>> completableFuture : completableFutures) {
            List<String> list = completableFuture.get();
            res2.add(list);
        }

        Long end2 = System.currentTimeMillis();
        System.out.println("异步方法全部阻塞完毕，耗时:" + (end2 - end1));

    }

    /**
     * 场景：现在我们要异步得 rpc 调用6个接口，我们希望每个接口的响应时间在3s内，如果超过3s，那么就不等这个异步返回结果了
     * 解决方案：新加一个定时器线程，和我们异步调用rpc的方法配套使用 + applyToEitherAsync
     */
    static void testTimeOutFail() throws ExecutionException, InterruptedException {
        /**
         * 模拟6个请求三方服务的future
         * IntStream.range(0, 6)：相当于for循环 0 1 2 3 4 5
         * mapToObj：返回的是个流 可以直接收集(collect(Collectors.toList()))
         */
        List<CompletableFuture<String>> futureList = IntStream.range(0, 6).mapToObj(i -> queryProtoService("Service" + i)).collect(Collectors.toList());

        // 并发执行
        futureList.forEach(CompletableFuture::join);

        // 查看执行结果
        for (CompletableFuture<String> future : futureList) {
            //不是get的时候发生异常的，get之前已经发生了 异常并且把error这个返回结果放到结果集里面了
            System.out.println(future.get());
        }
    }

    private final static int AVALIABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    // 自定义一个线程池
    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(
            AVALIABLE_PROCESSORS,
            AVALIABLE_PROCESSORS * 2,
            5,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(5),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    /**
     * 定时器线程，专门给fastFail使用
     */
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR = Executors.newScheduledThreadPool(
            1,//这里一个线程就够用了
            new BasicThreadFactory.Builder().namingPattern("ScheduleFuture-Thread-%d")
                    .daemon(true) // 这里需要设置成守护线程
                    .build());

    /**
     * 构造一个指定时间后抛异常的future
     */
    private static <T> CompletableFuture<T> fastFail(long timeout, TimeUnit timeUnit) {
        final CompletableFuture<T> future = new CompletableFuture<>();// 承载超时异常的future

        SCHEDULED_EXECUTOR.schedule(() -> {
            final TimeoutException ex = new TimeoutException("超时啦... " + timeout);
            //就是给future一个异常，当future.get()的时候 会抛出我们设置的这个ex异常
            future.completeExceptionally(ex);
        }, timeout, timeUnit);

        return future;
    }

    /**
     * 用于构造模拟查询三方服务的future
     *
     * @return
     */
    private static CompletableFuture<String> queryProtoService(String serviceName) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                long t = ThreadLocalRandom.current().nextInt(5);//随机0~5s
                System.out.println(Thread.currentThread().getName() + " 请求服务 " + serviceName + " 需要时间:" + t + "秒");
                TimeUnit.SECONDS.sleep(t);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return serviceName;
        }, POOL_EXECUTOR)

                // applyToEitherAsync: 最快返回输出的线程结果作为下一次任务的输入
                // 所以这里理解: 模拟三方服务 futureA, futureB (fastFail), 则这个方法会返回 futureA 和 futureB 更快的那一个的执行结果去执行下一跳任务
                // futureB 的结果是固定的： timeout(这里是2s)时间之后抛一个超时异常
                // 则: futureA 如果在 timeout 之前返回，则 applyToEitherAsync 会返回 futureA 的值
                // 否则: futureA 如果在 timeout 之后返回，则 applyToEitherAsync 会返回 futureB 的值. 这里是超时异常, 返回 error

                /**
                 * fastFail和前面的CompletableFuture.supplyAsync中最快返回的执行下面的t->t。
                 * 如果是fastFail执行的快，那么就执行t->t，然后被exceptionally捕获
                 *
                 * 线程A仅需1秒即返回执行结果，而线程B需要长达5秒执行完毕才返回结果，那么就采用线程A执行的结果。
                 * https://blog.csdn.net/zhangphil/article/details/80883953
                 *
                 * 下面函数式接口 第一个是入参 第二个是返回值
                 * 这里的入参指的是fastFail的返回值和queryProtoService方法里面的 CompletableFuture.supplyAsync()的返回值
                 * 但是由于我们fastFail写的是泛型T,所以编译期没报错   CompletableFuture<T>
                 *
                 * 第二个返回值就是CompletableFuture<String> queryProtoService的返回值
                 *
                 *
                 * 所以现象：
                 * 第一个请求来了 在规定时间内执行完了 抢过了fastFail 执行下面的apply方法
                 * 第二个请求来了 没抢过fastFail 并且它还带着异常 直接就被exceptionally发现了(不走applyToEitherAsync的apply()方法) 返回了一个error字符串(不是get的时候触发异常然后被exceptionally捕获)
                 */
                .applyToEitherAsync(fastFail(2000, TimeUnit.MILLISECONDS), new Function<String, String>() {
                    @Override
                    public String apply(String s) {
                        return s;
                    }
                }).exceptionally(new Function<Throwable, String>() {
                    @Override
                    public String apply(Throwable throwable) {
                        return "error";
                    }
                });
    }

}
