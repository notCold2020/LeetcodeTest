package com.cxr.algorithm.other;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 一些思考：
 * 1。数据结构到底是怎样的 存了什么数据？
 * 我们操作的都是HashMap key可以是我们要存的缓存的名字 value可以是数据。 比如 "name" : "张三"
 * 我们希望实现的就是在get("name")的时候 这个value能自己往前移动到头部
 * 但是我们现在要实现lru 需要让他自己可以排序 那么只有hashmap是不够的 需要有一种可以前后移动的->双向链表
 * 所以数据结构变成了。我们存，正常存。但是真实的存储是冗余了一份key在双向链表中（双向链表也是有key value prev next属性的）
 * 而我们get的时候 是get的HashMap 返回value(DListNode).value 试问除了hashmap谁能通过key拿到value呢？-> 通过name拿张三
 *
 * 比如我们存key:1 value:1 那么永远都是这个关系 我们修改的并不是值 而且这个双向链表的顺序 也就是node节点的prev next指针
 * 我们不就是通过修改指针的方式变相改变节点顺序吗？！
 *
 * 2。一共new了几次DLinkedNode
 * 如果put10次 就是new 10次 因为每次如果不存在，那么就new 看源码啊大哥
 *
 * 3。至始至终有几个双向链表呢
 * 一个。cache这个map存的不过是个指针而已。我们都是在同一个链表上进行操作鸭
 */
public class LRUCache extends LinkedHashMap<Integer, Integer> {

    public static void main(String[] args) {
        LRU lruCache = new LRU(3);
        lruCache.put(1, 1);
        lruCache.put(1, 1);
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
            System.out.println("new了一次,key:" + _key + ",value:" + _value);
            key = _key;
            value = _value;
        }
    }

    private Map<Integer, DLinkedNode> cache = new HashMap<Integer, DLinkedNode>();
    private int size;//当前容量
    private int capacity;//最大容量
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
            //map的key存在，覆盖node的value，并把node移到头部
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
        //这俩顺序不能反
        head.next.prev = node;
        head.next = node;

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
