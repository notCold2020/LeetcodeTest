package com.cxr.designpatterns.RulesEngineBetter;

import com.cxr.designpatterns.RulesEngineBetter.abstractBaseNode.BaseNode;
import com.cxr.designpatterns.RulesEngineBetter.cache.NodeCache;
import com.cxr.designpatterns.RulesEngineBetter.model.CommonContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Date 2022/5/15 10:37 上午
 * @Created by CiXingrui
 */
@Component
public class Handler {


    public CommonContext handlerReq(CommonContext commonContext) {
        //根节点带children  这里的1就是场景 充值-1
        BaseNode root = NodeCache.cache.get("1");

        /**
         * 这个process是反过来的，先从baseNode基础节点(最抽象的那个抽象类)开始执行，然后发现自己要执行一个抽象方法
         * 抽象方法就是用来给子类继承实现的，所以开始执行子类的重写的方法，然后就开始走逻辑。
         */
        root.process(commonContext);

        return commonContext;
    }

    /**
     * {
     *     "scene":"充值-1",
     *     "roam":{
     *         "cost":120,
     *         "uid":18004
     *     }
     * }
     * @param args
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(Appconfig.class);

        Handler bean = annotationConfigApplicationContext.getBean(Handler.class);

        CommonContext commonContext = new CommonContext();
        commonContext.setTraceId(UUID.randomUUID().toString().substring(0, 16));
        Map<String, Object> map = new HashMap<>();
        map.put("cost", 120);
        map.put("uid", 18004);
        commonContext.setMap(map);

        CommonContext res = bean.handlerReq(commonContext);

    }

}

//@ComponentScan("com.cxr.designpatterns.RulesEngineBetter")
//@Configuration
class Appconfig {

}
