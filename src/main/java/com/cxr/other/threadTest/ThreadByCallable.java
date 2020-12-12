package com.cxr.other.threadTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadByCallable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableDemo callableDemo = new CallableDemo();
        //传一个实现callable接口的对象
        FutureTask<List<Integer>> futureTask = new FutureTask(callableDemo);
        new Thread(futureTask).start();
        //获取返回值
        futureTask.get().stream().forEach(System.out::println);

    }

}

class CallableDemo implements Callable<List<Integer>> {
    List<Integer> list = new ArrayList<>();

    @Override
    public List<Integer> call() throws Exception {
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        return list;
    }
}
