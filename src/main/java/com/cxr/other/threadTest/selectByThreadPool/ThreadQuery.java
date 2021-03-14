package com.cxr.other.threadTest.selectByThreadPool;


import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

public class ThreadQuery implements Callable<List<String>> {

    private String search;//查询条件 根据条件来定义该类的属性

    private int bindex;//当前页数

    private int num;//每页查询多少条

    private String table;//要查询的表名，也可以写死，也可以从前面传

    private List<String> page = new LinkedList();//每次分页查出来的数据

    public ThreadQuery(int bindex, int num, String table) throws InterruptedException {
        this.bindex = bindex;
        this.num = num;
        this.table = table;
    }

    @Override
    public List call() throws Exception {
        //返回数据给Future
        Thread.sleep(10000L);
//        page=sqlHadle.queryTest11(bindex,num,table);       模拟分页查询数据库数据
        page.add("结果"+bindex);
        return page;
    }
}
