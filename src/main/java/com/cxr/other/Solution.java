package com.cxr.other;

import com.cxr.LeecodetestApplication;

import java.util.*;

/**
 * 输入: s = "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 */
class Solution {
    /**
     * map (k, v)，其中 key 值为字符，value 值为字符位置;
     */
    public static int lengthOfLongestSubstring(String s) {
        int length = s.length();
        int max = 0;

        Map<Character, Integer> map = new HashMap<>();
        for (int start = 0, end = 0; end < length; end++) {
            char element = s.charAt(end);
            if (map.containsKey(element)) {
                start = Math.max(map.get(element) + 1, start); //map.get()的地方进行+1操作
            }
            max = Math.max(max, end - start + 1);
            map.put(element, end);
        }
        return max;
    }

    public static void main(String[] args) {
        lengthOfLongestSubstring("pwwkew");
    }
}
