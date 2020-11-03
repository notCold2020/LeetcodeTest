package com.lrw.ohter;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.alibaba.dubbo.config.spring.ServiceBean;
import com.alibaba.dubbo.registry.integration.RegistryProtocol;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Solution {

    public static void main(String[] args) {

//        String format = String.format("%.2f", Double.parseDouble("0.60"));
//        String format1 = new DecimalFormat("0.00%").format(Double.parseDouble(String.format("%.2f", Double.parseDouble("1.00"))));
        int i = 0;
        int a = i++;
        System.out.println(a);

    }

}
