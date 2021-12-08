package com.cxr.other.threadTest.selectByThreadPool;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

@Service
public class TheardQueryService {

    /**
     * 场景：现在要从数据库读取1w条数据
     * 问题点：如果单线程读取，太慢了，比如读取2500条就3s，那么需要12s
     * 解决方案：多线程读取,每个线程读2500条，分页读
     * 结果：只需3s
     */
    public static List<List<String>> queryByThread(String table, Integer count, Integer num) throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        //返回结果
        List<List<String>> result = new ArrayList<>();

        //查询数据库总数量/每页数 = 需要查询的次数
        int times = count / num;
        if (count % num != 0) {
            times = times + 1;
        }

        //开始页数  连接的是orcle的数据库  封装的分页方式  我的是从1开始 ,并且这步还没有并发分体呢，页数就是同步地放进ThreadQuer里面
        int bindex = 1;

        //任务的list
        List<Callable<List<String>>> tasks = new ArrayList<>();
        for (int i = 0; i < times; i++) {
            Callable<List<String>> qfe = new ThreadQuery(bindex, num, table);
            tasks.add(qfe);
            bindex++;
        }
        //定义固定长度的线程池  防止线程过多
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        /**
         * invokeAll 用于执行任务并且获取结果 这个玩意是阻塞的
         * 会把传进来这个tasks 全都执行完了 再往下走 顺便封装个返回结果
         * 也就是说 获取到的结果必然是完整的
         *
         * 而且invokeAll()是有顺序的，结果的顺序刚好就是下面tasks的任务list的顺序(tasks因为是分页，已经有序了)
         * todo:有序的原理
         * 换句话说:invokeAll的意思是 把tasks任务队列里面的任务用我们的线程池来按照任务队列的来执行，执行完之后再按照任务队列的
         *          顺序把每个任务产生的结果放到一个list，因为这个只是任务队列，我们的任务还没开始执行呢所以可以保证顺序
         */
        Collections.reverse(tasks);
        List<Future<List<String>>> futures = executorService.invokeAll(tasks);
        //处理线程返回结果
        if (futures != null && futures.size() > 0) {
            for (Future<List<String>> future : futures) {
                List<String> list = future.get();
                result.addAll(Collections.singleton(list));
            }
        }
        //关闭线程池 调用shutdown()方法之后 才能调用isTerminated() 不然永远返回true
        executorService.shutdown();
        long end = System.currentTimeMillis();
        result.forEach(m -> {
            System.out.println(JSONObject.toJSONString(m));
        });
        System.out.println("4个线程查询数据用时:" + (end - start) + "ms");
        return result;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        queryByThread("表名", 10000, 2500);
    }
}
