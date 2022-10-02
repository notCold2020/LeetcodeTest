package com.cxr.designpatterns.responsibilityChainMethod.betterResponsibilityChainMethod;

import java.util.List;

/**
 */
public abstract class AbstractHandler {

    //责任链中的下一个对象 抽象类注入自身
    private AbstractHandler nextHandler;

    /**
     * 责任链的下一个对象
     */
    public void setNextHandler(AbstractHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    /**
     * 具体参数拦截逻辑,给子类去实现
     *
     * 这个方法在抽象类里面已经有实现了，如果类A继承抽象类，不会实现这个filter方法
     */
    public void filter(List<String> request, List<String> response) {
        /**
         * 这个责任链怎么终止的？
         *
         * 会执行完，报错了会终止
         * 这个doFilter，是活的。
         *
         * 这里是个抽象方法， 得找子类具体的实现，this当前对象是已经初始化过的了，所以走到了子类实现了doFilter方法
         */
        doFilter(request, response);

        //上面执行完了走到这里
        if (getNextHandler() != null) {
            /**
             * 这个判空也就是链还没执行完
             * 1.getNextHandler()拿到下一个节点
             * 2.调用下一个节点的filter【下一个节点继承了抽象类，所以会有抽象类里面的普通方法-filter方法】
             * 3.this对象为下一个节点
             * 4.this.doFilter()，这就是下一个节点的doFilter，也就是下一个实现类的方法，开始执行业务逻辑
             * 5.执行完了又判断if (getNextHandler() != null) {}
             *
             * 重点：说明抽象类的filter是可复用的，每个实现类都执行了异常filter方法，
             * 而filter方法里面的doFilter方法是活的，走到DoFilter方法就调用子类自己的实现了。
             */
            getNextHandler().filter(request, response);
        }
    }

    public AbstractHandler getNextHandler() {
        return nextHandler;
    }

    /**
     * 这里的req 和resp
     * 不一定是集合，是个大对象也可以，都包起来
     * 但是String不行
     */
    abstract void doFilter(List<String> filterRequest, List<String> response);

}
