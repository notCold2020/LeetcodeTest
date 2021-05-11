package com.cxr.algorithm.other;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache extends LinkedHashMap<Integer, Integer> {

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(3);
        for (int i = 0; i < 10; i++) {
            lruCache.put(i, i);
        }

    }

    private int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75F, true);
        this.capacity = capacity;
    }

    public int get(int key) {
        return super.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        super.put(key, value);
    }

    /**
     * 每一次put 都会触发这个方法 如果返回true 就删除掉最后的元素
     * 我的理解应该就是最旧的元素
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;
    }
}

/**
 * 用了一个map来存储 key是key value是个双向链表（key value）
 * 我们需要这个链表来对这个map进行排序
 */
class LRU {
    class DLinkedNode {
        int key;
        int value;
        DLinkedNode prev;
        DLinkedNode next;

        public DLinkedNode() {
        }

        public DLinkedNode(int _key, int _value) {
            key = _key;
            value = _value;
        }
    }

    private Map<Integer, DLinkedNode> cache = new HashMap<Integer, DLinkedNode>();
    private int size;
    private int capacity;
    private DLinkedNode head, tail;

    public LRU(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        // 使用伪头部和伪尾部节点
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            return -1;
        }
        // 如果 key 存在，先通过哈希表定位，再移到头部(get操作相当于吧这个数据变成最新的了)
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            // 如果 key 不存在，创建一个新的节点
            DLinkedNode newNode = new DLinkedNode(key, value);
            // 添加进哈希表
            cache.put(key, newNode);
            // 添加至双向链表的头部
            addToHead(newNode);
            size++;
            if (size > capacity) {
                // 如果超出容量，删除双向链表的尾部节点
                DLinkedNode tail = removeTail();
                // 删除哈希表中对应的项
                cache.remove(tail.key);
                size--;
            }
        } else {
            // 如果 key 存在，先通过哈希表定位，再修改 value，并移到头部
            node.value = value;
            moveToHead(node);
        }
    }

    private void addToHead(DLinkedNode node) {
        /**
         * 先解决node 让node的前驱节点和后继节点不是null
         * node.prev = head;
         * node.next = head.next;
         * 至此 <- node ->  ⬅️ 长这个样子
         * node的前驱节点的next指针指向node node.prev.next = node
         * node的后继节点的prve指针指向node node.next.prev = node;
         */
        node.prev = head;
        node.next = head.next;
        head.next = node;
        head.next.prev = node;
    }

    private void removeNode(DLinkedNode node) {
        /**
         * 移除当前node 就是这个入参
         * node的前驱节点的next指针 指向node的下一个节点
         * node的后继节点的prex指针指向node的上一个节点
         * 第一个.代表的是节点，第二个.代表的是指针。
         *
         * 至此node节点被移出来了 但是node的前后两个指针依旧指向原来node的前后两个节点
         */
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(DLinkedNode node) {
        removeNode(node);
        addToHead(node);
    }

    private DLinkedNode removeTail() {
        //直接remove掉tail虚拟节点掉prev节点就可以了
        DLinkedNode res = tail.prev;
        removeNode(res);
        return res;
    }
}
