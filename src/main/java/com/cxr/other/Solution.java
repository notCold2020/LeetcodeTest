package com.cxr.other;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

@Data
@Component
@Slf4j
public class Solution {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap<>();
        Object o2 = concurrentHashMap.putIfAbsent("1", 123);
        Object o1 = concurrentHashMap.putIfAbsent("1", 1234);
        Object o = concurrentHashMap.get("1");

    }

}




