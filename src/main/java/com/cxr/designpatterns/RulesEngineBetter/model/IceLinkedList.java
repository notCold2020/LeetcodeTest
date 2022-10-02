package com.cxr.designpatterns.RulesEngineBetter.model;

import lombok.Data;

@Data
/**
 * 子节点信息，其实就是存了双向链表的首尾元素
 * 其实所有的节点都在这条双向链表上
 */
public final class IceLinkedList<E> {
    /**
     * 节点children中的最上层节点
     */
    private Node<E> first;

    /**
     * 节点children中的最下层节点
     */
    private Node<E> last;

    /**
     * 直系children的数量
     */
    private int size;

    public boolean isEmpty() {
        return this.size == 0;
    }


    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    //在尾部添加节点
    void linkLast(E e) {
        final Node<E> la = last;
        final Node<E> newNode = new Node<>(la, e, null);
        last = newNode;
        if (la == null) {
            first = newNode;
        } else {
            la.next = newNode;
        }
        size++;
    }

    /**
     * 其实 这不就是个双向链表吗
     */
    public static class Node<E> {

        //当前节点信息
        public E item;

        //同一列的下一个
        public Node<E> next;

        //同一列的上一个
        public Node<E> prev;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
