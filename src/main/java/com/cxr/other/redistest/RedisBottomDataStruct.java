package com.cxr.other.redistest;

import org.omg.IOP.Encoding;

import java.util.HashSet;

//redis底层数据结构
public class RedisBottomDataStruct {
}


/**
 * String 类型的底层数据结构
 * SDS ： simple dynamic string
 */
class SDS {
    int len;//存储的字符串长度
    char[] buf;//char型数组保存字符串的每个元素
    int free;//buf 中未使用的字节数量（这里应该是说字符数组中剩余的位置，当free小于某个值时会进行扩容）


    /**
     redis为什么不适用C语言自带的字符串
     1. 获取字符串长度是O(1)级别
     2. C语言在拼接字符串时可能会造成缓存区溢出，SDS可以自动扩容(没用final修饰)
     3. 可以保存二进制数据
     */
}

/**
 * List 类型底层数据结构
 * redis List特性
 * 1.双向链表，获取前一个节点和后一个节点，时间复杂度都为O(1)
 * 2.带节点计数器，获取链表长度为O(1)
 * 3.无环，前后节点都为null
 * 综上： redis List 类型底层数据结构是一个普通的双向链表
 */
class List {
    Object val; //节点值
    ListNode head; //头节点
    ListNode tail; //尾节点
    int len;//链表所包含的数据量

    //节点复制函数
    ListNode copyNode(ListNode node) {
        return node;
    }

    //释放节点（删除节点）函数
    void freeNode(ListNode node) {
    }

    //节点值对比函数
    void compareNode(ListNode node1, ListNode node2) {
    }

    //内部类，redis C语言中，节点类并不是写在内部的
    class ListNode {
        ListNode prev;//前节点
        ListNode next; //后一个节点
    }
}

//Hash 类型数据结构，不再赘述，哈希表，完全类似于HashMap ,只是扩容非常频繁
class Hash {
/**
 负载因子： 节点数/哈希表大小
 扩容条件： 没有执行bgsave命令且负载因子>=1  ,执行bgsave命令且负载因子>=5
 值得说的是，在扩容之后，并不是一瞬间完成rehash操作的，因为当key的数据量很大的时候
 其他操作不能进行，所以只能是渐进式hash。删除和更新会在2张hash表上完成，查询是先查
 第一张表，查不到再差第二张表。增加，是增加再新表上

 */
}

//set 底层数据结构
class set {
    // 2种数据类型是互斥的 使用intset，则存的这个key集合中存的都是整数，而且元素数量小于512个
    // 否则使用 hash表
    Intset intset;
    HashSet set;//HashSet的底层不也是hashmap吗

    //内部类，同ListNode，实际上并不是内部类
    class Intset {
        Encoding encoding;//编码方式
        int len;  //set集合包含的元素数量
        Integer[] values; //保存元素数组
    }
}

//###########################着重讲zest###############################################
//zset 底层数据结构 --> skipList zipList,3.2之后使用quickList
class zset {
    /**
     * redis为了解决空间 zset和hash在元素个数较少的时候 会用ziplist存储
     */
    ZipList zipList;
    SkipList skipList;
/*======================================================================================================*/
    /**
     * quicklist其实就是linkedlist + ziplist,数据少的时候用压缩链表 数据多了变成quicklist
     * 把每个quicklist相连，每个quicklist内部有个指向zipList的指针
     */
}

class ZipList<T> { //压缩列表
    int zlbytes; //整个压缩篇列表占用字节数
    int zltail_offset; //最后一个元素距离压缩列表起始位置的偏移量，用于快速定位到最后一个节点
    int zllength; //元素个数
    T[] entries; //元素内容列表

    class entry{
        //前一个entry的字节长度，通过这个字段快速定位到下一个元素
        int prevlen;
        int encoding; //元素类型编码
        byte[] content; //元素内容
    }
}

class ZipListNode {
    Object value;//值
    double score;//分数
    ZipListLevel[] zipListLevels;//所在的层数

    //真的内部类
    class ZipListLevel {
        ZipListNode prev; //前节点
        int span;//跨度
    }
}


class SkipList {
/**
 * 为什么用跳表？
 * 单链表是有序的 但是查询某个数据需要遍历 我们可以加索引 本质上这就是跳表
 * 就像二分查找 但是二分查找是需要数组的 咱们这个是链表 于是就把链表稍加改造 改造后的这个玩意就是跳表
 * 时间复杂度O(logn)
 * 为什么不用二分查找那种 上层节点是下层节点的一般 这样复杂度是严格O(logn)了呀  ?
 * 不好维护 上册要是下层1/2 新增节点 都得变 退化成O(n)了
 * 所以我们用随机层数
 * ->新增节点不影响其他节点的层数
 *
 */
}


