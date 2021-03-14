package com.cxr.other;

public class ListNode {
    public int val;
    public ListNode next;
    ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static void main(String[] args) {
        String replace = "ss".replace('s', 'o');
        System.out.println(replace);
    }
}
