package com.cxr.algorithm.link.simple;

import com.cxr.other.ListNode;

public class intersectLink {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        /**
         * 如果量两个链表一样长并且肯定相交 那么就很简单 只要判断headA == headB就可以求出相交位置
         * 但是现在AB 不一样长 有长度差 所以关键就在于把长度差干掉
         * 所以把A和B拼在了一起 消除了长度差
         * 如果headA == headB 就还是相交（相交处后面的链表是相同的 是公共部分）
         * 如果不相交 就会在null处相遇 毕竟拼接后的链表长度是相等的
         *
         **/
        if (headA == null || headB == null) {
            return null;
        }
        ListNode pA = headA, pB = headB;
        // 在这里第一轮体现在pA和pB第一次到达尾部会移向另一链表的表头, 而第二轮体现在如果pA或pB相交就返回交点, 不相交最后就是null==null
        while (pA != pB) {
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;
    }
}
