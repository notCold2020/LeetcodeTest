package com.cxr.designpatterns.responsibilityChainMethod.betterResponsibilityChainMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component("ChainPatternDemo")
public class ChainPatternDemo {

    /**
     * 我感觉这个list的顺序就是@Order的顺序
     * 自动注入各个责任链的对象-即校验的顺序
     */
    @Autowired
    private List<AbstractHandler> abstractHandleList;

    private AbstractHandler abstractHandler;

    //spring注入后自动执行，责任链的对象连接起来
    @PostConstruct
    public void initializeChainFilter() {

        for (int i = 0; i < abstractHandleList.size(); i++) {
            if (i == 0) {
                /**
                 * 这不是在初始化的时候 abstractHandler = new xxxx() 了吗
                 */
                abstractHandler = abstractHandleList.get(0);
            } else {
                AbstractHandler currentHander = abstractHandleList.get(i - 1);
                AbstractHandler nextHander = abstractHandleList.get(i);
                currentHander.setNextHandler(nextHander);
            }
        }
    }

    //直接调用这个方法使用
    public List<String> exec(List<String> request, List<String> response) {
        /**
         * 这里在第一次调用，下面的abstractHandler已经是被初始化过的了
         * 因为这个是普通方法，所以会走到抽象类的filter方法
         */
        abstractHandler.filter(request, response);
        return response;
    }

    public AbstractHandler getAbstractHandler() {
        return abstractHandler;
    }

    public void setAbstractHandler(AbstractHandler abstractHandler) {
        this.abstractHandler = abstractHandler;
    }
}
