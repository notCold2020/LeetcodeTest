package com.cxr.other.threadTest.selectByThreadPool;

import cn.hutool.json.JSONUtil;
import com.cxr.other.threadTest.selectByThreadPool.ThreadQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by df on 2018/9/20.
 */

@Service
public class TheardQueryService {


    public static List<List<String>> getMaxResult(String table) throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();//开始时间
        List<List<String>> result = new ArrayList<>();//返回结果
        //查询数据库总数量
        int count = 100000;
        int num = 8000;//一次查询多少条
        //需要查询的次数
        int times = count / num;
        if (count % num != 0) {
            times = times + 1;
        }
        //开始页数  连接的是orcle的数据库  封装的分页方式  我的是从1开始
        int bindex = 1;
        //Callable用于产生结果
        List<Callable<List<String>>> tasks = new ArrayList<>();
        for (int i = 0; i < times; i++) {
            Callable<List<String>> qfe = new ThreadQuery(bindex, num, table);
            tasks.add(qfe);
            bindex += bindex;
        }
        //定义固定长度的线程池  防止线程过多
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        /**
         * invokeAll 用于执行任务并且获取结果 这个玩意是阻塞的
         * 会把传进来这个tasks 全都执行完了 再往下走 顺便封装个返回结果
         * 也就是说 获取到的结果必然是完整的
         */
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
        System.out.println(JSONUtil.toJsonStr(result));
        System.out.println("线程查询数据用时:" + (end - start) + "ms");
        return result;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        getMaxResult("x");
    }
}
