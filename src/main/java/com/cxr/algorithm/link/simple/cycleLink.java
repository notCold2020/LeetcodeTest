package com.cxr.algorithm.link.simple;

import com.cxr.other.ListNode;

import java.util.HashSet;
import java.util.Set;

public class cycleLink {
    public boolean hasCycle(ListNode head) {
        if (head == null)
            return false;
        //快慢两个指针
        ListNode slow = head;
        ListNode fast = head;
        //如果成环了 快指针必然走了回来 如果走回来了 快指针必然和满指针相遇（快指针追上慢指针）
        //说白了如果成环 快指针必然停不下来 先
        while (fast != null && fast.next != null) {
            //慢指针每次走一步
            slow = slow.next;
            //快指针每次走两步
            fast = fast.next.next;
            //如果相遇，说明有环，直接返回true
            if (slow == fast)
                return true;
        }
        //否则就是没环
        return false;
    }

    /**
     * 最容易想到的方法是遍历所有节点，每次遍历到一个节点时，判断该节点此前是否被访问过。
     * <p>
     * 具体地，我们可以使用哈希表来存储所有已经访问过的节点。每次我们到达一个节点，
     * 如果该节点已经存在于哈希表中，则说明该链表是环形链表，否则就将该节点加入哈希表中。重复这一过程，直到我们遍历完整个链表即可。
     *
     * @param head
     * @return
     */
    public boolean hasCycle2(ListNode head) {
        Set<ListNode> seen = new HashSet<ListNode>();
        while (head != null) {
            if (!seen.add(head)) {
                return true;
            }
            head = head.next;
        }
        return false;
    }
}
