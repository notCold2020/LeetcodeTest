package com.cxr.other;


import cn.hutool.Hutool;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 主要思路：
 * 要想找到一个只比当前数大一点点的肯定是从后面找第一个小数和比他大一点点的后面的数交换，交换完了发现还能再小点，那就增序排列一下。
 * <p>
 * 1.从后往前找第一个降序的数 k1
 * 2.从k1往后找第一个比k1大的数 k2
 * 3.交换k1 k2位置
 * 4.把k1后面的数字增序排列（大的数字肯定要放在后面）
 */
class Solution {

    public static void main(String[] args) {
    }
}

