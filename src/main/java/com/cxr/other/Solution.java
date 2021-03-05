package com.cxr.other;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONString;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;


public class Solution {
    public ArrayList<String> permutation(String str) {
        ArrayList<String> list = new ArrayList<>();
        if (str == null || str.length() == 0) {
            return list;
        }

        collect(str.toCharArray(), 0, list);
        // 保证字典序
        Collections.sort(list);
        return list;
    }

    //获得chars数组从begin开始(固定begin)的全排列都放到list中
    private void collect(char[] chars, int begin, ArrayList<String> list) {
        if (begin == chars.length - 1) {
            // 想一想树 只有走到树的末尾 也就是begin走到length-1了
            String s = String.valueOf(chars);
            if (!list.contains(s)) {
                list.add(s);
                return;
            }
        }
        /**
         * 比如abcd 交换之后 aa交换 ab ac ad
         *          begin+1 bb bc bd
         */
        for (int i = begin; i < chars.length; i++) {
            swap(chars, i, begin);
            //对除第一个字符外的字符序列进行递归
            collect(chars, begin + 1, list);
            swap(chars, i, begin);
        }

    }

    private void swap(char[] chars, int i, int j) {
        char temp = chars[j];
        chars[j] = chars[i];
        chars[i] = temp;
    }

}
