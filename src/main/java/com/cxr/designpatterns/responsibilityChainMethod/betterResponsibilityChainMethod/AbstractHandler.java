package com.cxr.designpatterns.responsibilityChainMethod.betterResponsibilityChainMethod;

import org.omg.CORBA.Request;

import javax.xml.ws.Response;

/**
 * 关注公众号：捡田螺的小男孩
 */
public abstract class AbstractHandler {

    //责任链中的下一个对象
    private AbstractHandler nextHandler;

    /**
     * 责任链的下一个对象
     */
    public void setNextHandler(AbstractHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    /**
     * 具体参数拦截逻辑,给子类去实现
     */
    public void filter(Request request, Response response) {
        doFilter(request, response);
        if (getNextHandler() != null) {
            //这个判空也就是链还没执行完
            getNextHandler().filter(request, response);
        }
    }

    public AbstractHandler getNextHandler() {
        return nextHandler;
    }

    abstract void doFilter(Request filterRequest, Response response);

}
