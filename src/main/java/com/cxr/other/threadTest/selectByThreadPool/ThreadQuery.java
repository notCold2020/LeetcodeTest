package com.cxr.other.threadTest.selectByThreadPool;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ThreadQuery implements Callable<List<String>> {

    private String search;//查询条件 根据条件来定义该类的属性

    private int bindex;//当前页数

    private int num;//每页查询多少条

    private String table;//要查询的表名，也可以写死，也可以从前面传

    //每次每个线程分页查出来的数据
    private List<String> page = new ArrayList<>();

    public ThreadQuery(int bindex, int num, String table) throws InterruptedException {
        this.bindex = bindex;
        this.num = num;
        this.table = table;
    }

    @Override
    public List<String> call() throws Exception {
        Thread.sleep(3000L);
        //模拟分页查询数据库数据
        String res = Thread.currentThread().getName() + "从数据库中查询了第" + bindex + "页的" + num + "条数据,当前的数据是" + (bindex - 1) * (num) + "条~" + bindex * (num) + "条";
        page.add("结果::" + res);
        return page;
    }
}
