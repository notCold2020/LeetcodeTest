package com.cxr.other;


import com.cxr.other.demo.entriy.User;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 给你两个链表 list1 和 list2 ，它们包含的元素分别为 n 个和 m 个。
 * 请你将 list1 中第 a 个节点到第 b 个节点删除，并将list2 接在被删
 * <p>
 * 输入：list1 = [0,1,2,3,4,5], a = 3, b = 4, list2 = [1000000,1000001,1000002]
 * 输出：[0,1,2,1000000,1000001,1000002,5]
 */
class Solution {
    public static ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {
        ListNode nodeA = list1;
        for (int i = 0; i < a - 1; i++) {
            nodeA = nodeA.next;
        }
        //找到a-1节点 说白了 弄了个指针
        ListNode nodeB = nodeA.next;
        for (int i = 0; i < (b - a + 1); i++) {
            //改变ndoeB 指针的位置
            nodeB = nodeB.next;
        }
        //nodeA指针指向的位置之后的数据改变了 list1也随之改变
        nodeA.next = list2;
        while (nodeA.next != null) {
            //改变nodeA指针的位置
            nodeA = nodeA.next;
        }
        //同理list1改变
        nodeA.next = nodeB;
        return list1;
    }

}
