package com.cxr.other;

import com.cxr.other.utilsSelf.MySelfEnum;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import javax.swing.*;
import java.util.*;

/**
 * 快速排序
 */
@Component
class Solution implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("onApplicationEvent----------------");
    }

    Solution() {
        System.out.println("构造方法");
    }
}

