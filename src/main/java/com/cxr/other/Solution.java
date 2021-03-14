package com.cxr.other;

import com.cxr.other.redistest.set;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 输入：candidates = [2,3,6,7], target = 7,
 * 所求解集为：
 * [
 * [7],
 * [2,2,3]
 * ]
 */
class Solution {
    public int[] fairCandySwap(int[] A, int[] B) {
        int Asum = 0;
        int Bsum = 0;
        //求A数组和
        List<Integer> list = new ArrayList<>();
        for (int a : A) {
            Asum += a;
            list.add(a);
        }
        //B和
        for (int b : B) {
            Bsum += b;
        }
        for (int j = 0; j < B.length; ++j) {
            int temp = (Asum - Bsum + B[j] * 2) / 2;
            //发现
            if (list.contains(temp)) {
                return new int[]{temp, B[j]};
            }
        }
        return new int[]{};

    }
}
