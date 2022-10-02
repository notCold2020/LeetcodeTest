package com.cxr.other.strangeDemo;

import com.cxr.other.demo.entriy.User;
import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class collectionsTestTest {

    @Test
    /**
     * 没啥好说的，排序
     */
    public void sort() {
        java.util.ArrayList<Integer> numbers = new ArrayList<>();
        // Add elements
        numbers.add(4);
        numbers.add(2);
        numbers.add(3);
        System.out.println("Unsorted ArrayList: " + numbers);
        Collections.sort(numbers);
        System.out.println("Sorted ArrayList: " + numbers);
    }

    @Test
    /**
     * 打散，就是重新随机排序
     * 如果运行两次，那么两次的结果也不一样
     */
    public void shuffle() {
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        System.out.println("Sorted ArrayList: " + numbers);
        Collections.shuffle(numbers);//打散
        System.out.println("ArrayList using shuffle: " + numbers);
    }

    @Test
    /**
     * 翻转，没啥说的
     */
    public void reverse() {
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        System.out.println("ArrayList1: " + numbers);

        Collections.reverse(numbers);
        System.out.println("Reversed ArrayList1: " + numbers);
    }

    @Test
    /**
     * 这个很鸡肋，填充，用下面的12345把numbers中的每一项都替换掉
     * ArrayList1: [1, 2]
     * ArrayList1 using fill(): [12345, 12345]
     */
    public void fill() {
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        System.out.println("ArrayList1: " + numbers);
        Collections.fill(numbers, 12345);
        System.out.println("ArrayList1 using fill(): " + numbers);
    }

    @Test
    /**
     * 1.不存在深拷贝浅拷贝的问题
     * 2.插入是在集合的末尾插入,当然也可以指定插入位置，比如【
     *      [1,2,3] addAll(1,[4,5,6])  -->  [1,4,5,6,2,3]
     * 】
     * 3.list和collections都有addAll方法
     * list的能指定顺序，collections的只是在末尾插入
     */
    public void addAll() {
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        System.out.println("ArrayList1: " + numbers);
        List<Integer> newNumbers = new ArrayList<>();
        newNumbers.addAll(numbers);//这里用的是List
        newNumbers.addAll(1, numbers);
        System.out.println("ArrayList2 using addAll(): " + newNumbers);

        Collections.addAll(numbers, 7, 8, 9);
        System.out.println("Collections.addAll: " + numbers);
    }

    @Test
    /**
     * 交换 会有IndexOutOfBoundsException
     */
    public void swap() {
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        System.out.println("ArrayList1: " + numbers);


        // Using swap()
        Collections.swap(numbers, 1, 2);
        System.out.println("ArrayList1 using swap(): " + numbers);
    }

    @Test
    /**
     * 二分查找，前提是集合本身有序，因为二分查找的前提就是有序。
     * 如果元素存在于集合，那就返回索引
     * 不存在，就返回范围区间的负数
     * 比如70 --> -6
     *     5 --> -3
     */
    public void binarySearch() {
        // Creating an ArrayList
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(3);
        numbers.add(6);
        numbers.add(9);
        numbers.add(14);

        // Using binarySearch()
        int pos = Collections.binarySearch(numbers, 14);
        System.out.println("The position of 3 is " + pos);
    }

    @Test
    /**
     * 获取对象在集合中第一次\最后一次出现的索引
     * 如果没出现就返回-1
     *
     * 就是遍历 用的equals方法 自然比较不带地址比较的是字面值
     */
    public void indexOf() {
        ArrayList<User> numbers2 = new ArrayList<>();
        numbers2.add(new User(1, "1"));
        numbers2.add(new User(2, "1"));
        numbers2.add(new User(3, "1"));
        numbers2.add(new User(4, "1"));
        numbers2.add(new User(5, "1"));
        numbers2.add(new User(5, "1"));

        int i = numbers2.indexOf(new User(5, "1"));
        int i2 = numbers2.lastIndexOf(new User(5, ""));
        System.out.println("i:" + i);
        System.out.println("i2:" + i2);
    }

    /**
     * 在直接操作xml的sql时候，人家非得要一个list(入参)那么我们如果只有一条数据还想用list查就可以用这个
     * 注意是不可变的
     */
    @Test
    public void singleton() {
        Set<String> set = Collections.singleton("a");
//        set.add("b");  java.lang.UnsupportedOperationException
        List<String> list = Collections.singletonList("a");
//        list.add("xx"); java.lang.UnsupportedOperationException
        Map<String, String> map = Collections.singletonMap("A->", "A");

        //同理这玩意也会报错,返回的都是不可变集合
        Map emptyMap = Collections.EMPTY_MAP;
//        Object put = emptyMap.put("1", "2");
        List<Object> objects = Collections.emptyList();
        Set<Object> objects1 = Collections.emptySet();

    }

    @Test
    public void testUnion() {
        String[] arrayA = new String[]{"A", "B", "C", "D", "E", "F"};
        String[] arrayB = new String[]{"B", "D", "F", "G", "H", "K"};
        List<String> listA = Arrays.asList(arrayA);
        List<String> listB = Arrays.asList(arrayB);
        //2个数组取并集
//        System.out.println(ArrayUtils.toString(CollectionUtils.union(listA, listB)));
        //[A, B, C, D, E, F, G, H, K]
    }


    @Test
    public void retainAllSubtract() {
        List<String> list1 = Lists.newArrayList("a", "b", "c");
        List<String> list2 = Lists.newArrayList("a", "b", "c", "d", "e");
        List<String> list3 = Lists.newArrayList("d", "c", "e", "c", "f");

        // list1 与  list2的交集
        System.err.println("list1 与  list2的交集 " + CollectionUtils.retainAll(list1, list2));
        // list1 与  list3的差集  ： 1减去3的元素
        System.err.println("list1 与  list3的差集 " + CollectionUtils.subtract(list1, list3));
        // list3 与  list1的差集  ： 3减去1的元素
        System.err.println("list3 与  list1的差集 " + CollectionUtils.subtract(list3, list1));

        //这里有个很好的写法，AB取交集，再从A中把AB交集去掉，这不就把A中B里面的元素去掉了吗
    }

    @Test
    public void equals() {
        List<User> listA = Collections.singletonList(new User(1,"1"));
        List<User> listB = Collections.singletonList(new User(1,"1"));
        boolean equalCollection = CollectionUtils.isEqualCollection(listA, listB);
        System.out.println(equalCollection);//true 只比较字面值
    }

    @Test
    public void contains() {
        List<User> listA = Collections.singletonList(new User(1,"1"));
        List<User> listB = Arrays.asList(new User(1,"1"),new User(2,"2"));
        boolean b = CollectionUtils.containsAll(listA,listB);
        boolean b1 = listB.containsAll(listA);
        System.out.println(b);//true 只比较字面值
        System.out.println(b1);//true 只比较字面值
    }

}










