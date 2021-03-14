package com.cxr.other.redistest;

import com.cxr.other.demo.entriy.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisTemplateDemoTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /*  https://blog.csdn.net/ruby_one/article/details/79141940
     * redisTemplate :如果存的是对象 当取出的时候 用这个👈 直接就能取出对象
     * stringRedisTemplate :如果存的是字符串 用这个👈 继承的redisTemplate 在构造函数里面定义了序列化过程
     * 👆 只能操作<String,String>
     *
     * 最大的区别就是序列化方式不同 前者是jdk的序列话方式 后者是String的序列化策略
     * 两者数据不互通的
     *
     * 存的时候前者存的key是字节数组 后者是存的是可视化的文本
     * keys * 的时候能看见是啥
     */
    @Test
    void test01() {
        //如果相同会被覆盖
        redisTemplate.opsForValue().set("张三", "张三123");
        Object zx1 = redisTemplate.opsForValue().get("张三");
        System.out.println(zx1);
        stringRedisTemplate.opsForValue().set("张三2", "张三1233");
        String zs2 = stringRedisTemplate.opsForValue().get("张三2");
        System.out.println(zs2);
//        User user = new User(1, "1", "张三");
        //存储对象也可以 Object强转
//        redisTemplate.opsForValue().set("66", user);
        User user1 = (User) redisTemplate.opsForValue().get("66");
        System.out.println(user1);

        //由于设置的是10秒失效，十秒之内查询有结果，十秒之后返回为null
        redisTemplate.opsForValue().set("name", "tom", 2, TimeUnit.SECONDS);
        redisTemplate.opsForValue().get("name");
        //从偏移量 offset 开始 覆盖字符串的值 在这个盲猜和这个偏移量有关 一个字母对应两个字节
        stringRedisTemplate.opsForValue().set("key", "hello world ！");
        stringRedisTemplate.opsForValue().set("key", "redis", 6);
        System.out.println(stringRedisTemplate.opsForValue().get("key"));

        System.out.println(redisTemplate.opsForValue().setIfAbsent("multi1", "multi1"));//false  multi1之前已经存在
        System.out.println(redisTemplate.opsForValue().setIfAbsent("multi111", "multi111"));//true  multi111之前不存在

        //multiSet void multiSet(Map<? extends K, ? extends V> m);
        //为多个键分别设置它们的值  当然也有multiSetIfAbsent
        //set用的是map,get用的是list
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("multi1", "multi1");
        maps.put("multi2", "multi2");
        maps.put("multi3", "multi3");
        stringRedisTemplate.opsForValue().multiSet(maps);
        List<String> keys = new ArrayList<>();
        keys.add("multi1");
        keys.add("multi2");
        keys.add("multi3");
        List<String> objects = stringRedisTemplate.opsForValue().multiGet(keys);
        System.out.println(objects); //结果：[multi1, multi2, multi3]
        //获取旧值返回新值
        stringRedisTemplate.opsForValue().set("key", "hello world");
        String key = stringRedisTemplate.opsForValue().getAndSet("key", "new hello world");
        //计数器 对 key = increment 的值 每次+5，如果最开始不存在 就按照默认值0来算
        redisTemplate.opsForValue().increment("increment", 5);
        Object increment = redisTemplate.opsForValue().get("increment");
        System.out.println("increment:" + increment);
        //支持double long
        redisTemplate.opsForValue().increment("double", 1.22);
        //如果key对应的值不存在 就相当于set
        stringRedisTemplate.opsForValue().append("appendTest", "Hello");
        // Hello
        System.out.println(stringRedisTemplate.opsForValue().get("appendTest"));
        stringRedisTemplate.opsForValue().append("appendTest", "world");
        // Helloworld
        System.out.println(stringRedisTemplate.opsForValue().get("appendTest"));
        //key 的 size
        Long key1 = stringRedisTemplate.opsForValue().size("key");
    }

    @Test
    void test02() {
        //从左边插入，即插入到列表头部
        redisTemplate.opsForList().leftPush("product:list", "iphone xs max");
        redisTemplate.opsForList().leftPush("product:list", "thinkpad x1 carbon");
        redisTemplate.opsForList().leftPush("product:list", "mackBook pro13");
        redisTemplate.opsForList().leftPush("product:list", "HuaWei Mate20 pro");
        //从左边插入一个数组
        String[] books = new String[]{"java编程思想", "springboot从入门到精通"};
        redisTemplate.opsForList().leftPushAll("book:list", books);
        //从左边插入一个集合
        List<String> list = new ArrayList<String>();
        list.add("鬼泣5");
        list.add("荒野大镖客2");
        list.add("仙剑奇侠传7");
        redisTemplate.opsForList().leftPushAll("game:list", list);
        //如果存在key对应的列表，则从左插入，不存在不做操作
        redisTemplate.opsForList().leftPushIfPresent("fruit:list", "1");
        //插入个集合
        redisTemplate.opsForList().leftPushAll("pushAll", Arrays.asList(1, 2, 3));
        //在key对应的列表中从左边开始找，找到第一个pivot，然后把value插到pivot左边，没有不做操作
        //也可以从右边插入，把下面的left改为right即可
        redisTemplate.opsForList().leftPush("product:list", "HuaWei Mate20X", "xiaomi mix");
        //指定位置重新设置指定值
        redisTemplate.opsForList().set("product:list", 1, "dell xps13");
        //删除和value相同的count个元素，count < 0，从右开始,count > 0，从左开始,count = 0，全部
        redisTemplate.opsForList().remove("product:list", -1, "HuaWei Mate20 pro");
        //获取制定下标对应的值 index,从0开始，有正负两套下标
        //[a,b,c,d] 下标有[0,1,2,3]和[0,-3,-2,-1];
        String value = (String) redisTemplate.opsForList().index("product:list", 1);
        System.out.println(value);
        //查询list中指定范围的内容 这个就是全查
        List<Object> list2 = redisTemplate.opsForList().range("product:list", 0, -1);
        System.out.println(list2);
        //修剪列表，使其只包含指定范围内的元素
        redisTemplate.opsForList().trim("product:list", 0, 2);
        redisTemplate.opsForList().trim("list", 1, -1);//裁剪第一个元素
        //查询列表长度
        System.out.println(redisTemplate.opsForList().size("product:list"));

        //弹出最左边元素
        redisTemplate.opsForList().leftPop("product:list");
        //移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时。
        redisTemplate.opsForList().leftPop("k1", 10, TimeUnit.SECONDS);
        //弹出最右边元素
        redisTemplate.opsForList().rightPop("product:list");
        //弹出k1最右边元素并放入k2最左边
        redisTemplate.opsForList().rightPopAndLeftPush("product:list", "game:list");
        List<Object> range = redisTemplate.opsForList().range("game:list", 0, -1);
        System.out.println("---");
        range.forEach(System.out::println);

        //将删除列表中存储的列表中第一次次出现的“setValue”。
        redisTemplate.opsForList().remove("listRight", 1, "setValue");
        System.out.println(redisTemplate.opsForList().range("listRight", 0, -1));
//        结果:[java, setValue, oc, c++]
//              [java, oc, c++]

    }

    @Test
    void test03() {
        redisTemplate.opsForHash().put("redisHash", "name", "tom");
        redisTemplate.opsForHash().put("redisHash", "age", 26);
        redisTemplate.opsForHash().put("redisHash", "class", "6");
        //只能给key设置过期时间
        redisTemplate.expire("redisHash", 100, TimeUnit.DAYS);
        Map<String, Object> testMap = new HashMap();
        testMap.put("name", "jack");
        testMap.put("age", 27);
        testMap.put("class", "1");
        redisTemplate.opsForHash().putAll("redisHash", testMap);
        Object o = redisTemplate.opsForHash().get("redisHash", "name");
        System.out.println(o);//被覆盖
        //删除hashkey 可以指定多个
        System.out.println(redisTemplate.opsForHash().delete("redisHash", "name", "age"));
        //判断hashkey是否存在
        redisTemplate.opsForHash().hasKey("redisHash", "age");

        List<Object> kes = new ArrayList<Object>();
        kes.add("name");
        kes.add("age");
        List<Object> redisHash = redisTemplate.opsForHash().multiGet("redisHash", kes);
//        结果：[jack, 28.1]
        //对hashkey 累加器
        redisTemplate.opsForHash().increment("redisHash", "age", 1);
        //获取hashkeys entries values
        Set<Object> redisHash12 = redisTemplate.opsForHash().keys("redisHash1");
        List<Object> redisHash1 = redisTemplate.opsForHash().values("redisHash1");
        Map<Object, Object> redisHash11 = redisTemplate.opsForHash().entries("redisHash1");
        //散列表大小个数
        redisTemplate.opsForHash().size("redisHash1");

//        结果：age:28.1
//        class:6
//        kkk:kkk
        Cursor<Map.Entry<Object, Object>> curosr = redisTemplate.opsForHash().scan("redisHash", ScanOptions.NONE);
        while (curosr.hasNext()) {
            Map.Entry<Object, Object> entry = curosr.next();
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

    }

    @Test
    void test04() {
        /**
         * set就是无序的 集合 自动去重
         * 所以pop是随机的
         */
        String[] strarrays = new String[]{"strarr1", "sgtarr2", "sgtarr2"};
        //返回成功插入元素的个数
        System.out.println(redisTemplate.opsForSet().add("setTest", strarrays));
        redisTemplate.opsForSet().remove("setTest1", strarrays);
        //随机pop出一个
        System.out.println(redisTemplate.opsForSet().pop("setTest1"));
        //相当于getall
        System.out.println(redisTemplate.opsForSet().members("setTest"));
        //将setTest 集合的 strarr1 转移到new集合
        redisTemplate.opsForSet().move("setTest", "strarr1", "new");
        Set<Object> aNew = redisTemplate.opsForSet().members("new");
        aNew.stream().forEach(System.out::println);
        /**
         * 还能求交集 求并集
         *
         */
    }

    @Test
    void test05() {
        //加入的时候顺势指定分数
        redisTemplate.opsForZSet().add("zset1", "zset-1", 1.0);
        redisTemplate.opsForZSet().add("zset1", "zset-2", 2.0);
        Double score = redisTemplate.opsForZSet().score("zset1", "zset-1");//如果没有就是null
        System.out.println("double:" + score);
        Set<Object> zset1 = redisTemplate.opsForZSet().range("zset1", 0, -1);
        zset1.forEach(System.out::println);
        //批量 这就是个value - score的结构 有getValue getScore方法
        ZSetOperations.TypedTuple<Object> objectTypedTuple1 = new DefaultTypedTuple<Object>("zset-5", 9.6);
        ZSetOperations.TypedTuple<Object> objectTypedTuple2 = new DefaultTypedTuple<Object>("zset-6", 9.9);
        Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<ZSetOperations.TypedTuple<Object>>();
        tuples.add(objectTypedTuple1);
        tuples.add(objectTypedTuple2);
        Long zset11 = redisTemplate.opsForZSet().add("zset1", tuples);
        System.out.println(zset11);
        System.out.println(redisTemplate.opsForZSet().range("zset1", 0, -1));

//        结果：2.2  原为1.1  增加元素的score值，并返回增加后的值
        System.out.println(redisTemplate.opsForZSet().incrementScore("zset1", "zset-1", 1.1));

//        结果：[zset-2, zset-1, zset-3, zset-4, zset-5]
//        0   //表明排名第一
        System.out.println(redisTemplate.opsForZSet().range("zset1", 0, -1));
        //获取元素的索引下标
        System.out.println(redisTemplate.opsForZSet().rank("zset1", "zset-2") + "dddd");

//        结果：[zset-2, zset-1, zset-3]
        System.out.println(redisTemplate.opsForZSet().rangeByScore("zset1", 0, 5));
    }

}
