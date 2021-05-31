package com.cxr.other.java8.collect;

import com.cxr.other.demo.service.UserService;
import com.cxr.other.java8.stream.Person;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
class TestForCollect {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * 归集(toList/toSet/toMap)
     */
    @Test
    void test01() {
        List<Integer> list = Arrays.asList(1, 6, 3, 4, 6, 7, 9, 6, 20);
        List<Integer> listNew = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
        Set<Integer> set = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toSet());

        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 24, "female", "New York"));

        Map<?, Person> map = personList.stream().filter(p -> p.getSalary() > 8000)
                .collect(Collectors.toMap(Person::getName, p -> p));
        System.out.println("toList:" + listNew);
        System.out.println("toSet:" + set);
        System.out.println("toMap:" + map);
    }

    /**
     * Collectors(收集)类提供了很多封装好的方法
     * Collector（收集）是个接口
     * <p>
     * 计数：count
     * 平均值：averagingInt、averagingLong、averagingDouble
     * 最值：maxBy、minBy
     * 求和：summingInt、summingLong、summingDouble
     * 统计以上所有：summarizingInt、summarizingLong、summarizingDouble
     */
    @Test
    void test02() {

        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));

        // 求总数
        Long count = personList.stream().collect(Collectors.counting());
        // 求平均工资
        Double average = personList.stream().collect(Collectors.averagingDouble(Person::getSalary));
        // 求最高工资
        Optional<Integer> max = personList.stream().map(Person::getSalary).collect(Collectors.maxBy(Integer::compare));
        // 求工资之和
        Integer sum = personList.stream().collect(Collectors.summingInt(Person::getSalary));
        // 一次性统计所有信息 summarizingDouble 意思是按照Double类型统计
        DoubleSummaryStatistics collect = personList.stream().collect(Collectors.summarizingDouble(Person::getSalary));

        System.out.println("员工总数：" + count);
        System.out.println("员工平均工资：" + average);
        System.out.println("员工工资总和：" + sum);
        System.out.println("员工工资所有统计：" + collect);
    }

    /**
     * partitioningBy：分割 把流按照某条件分割成几部分
     * groupingBy：分组 按照某属性开始分组
     */
    @Test
    void test03() {
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, "male", "New York"));
        personList.add(new Person("Jack", 7000, "male", "Washington"));
        personList.add(new Person("Lily", 7800, "female", "Washington"));
        personList.add(new Person("Anni", 8200, "female", "New York"));
        personList.add(new Person("Owen", 9500, "male", "New York"));
        personList.add(new Person("Alisa", 7900, "female", "New York"));

        // 将员工按薪资是否高于8000分组
        Map<Boolean, List<Person>> part = personList.stream().collect(Collectors.partitioningBy(x -> x.getSalary() > 80000));
        Map<String, List<Person>> group = personList.stream().collect(Collectors.groupingBy(x -> x.getSex()));

        // 将员工先按性别分组，再按地区分组 groupingBy的第二个参数还是个groupingBy
        Map<String, Map<String, List<Person>>> group2 = personList.stream().collect(Collectors.groupingBy(Person::getSex, Collectors.groupingBy(x -> x.getArea())));

        System.out.println("员工按薪资是否大于8000分组情况：" + part);
        System.out.println("员工按性别分组情况：" + group);
        System.out.println("员工按性别、地区：" + group2);
    }

    /**
     * 接合(joining) 将流中的元素用特定的连接符 （如果没有就直接连接） 形成一个字符串
     */
    @Test
    void test04() {
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));

        String collect = personList.stream().map(x -> x.getName()).collect(Collectors.joining(","));
        System.out.println("所有员工的姓名：" + collect);
    }

    /**
     * 归约(reducing)
     * Collectors.reducing()
     */
    @Test
    void test05() {
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));

        //0 + 8900 + 7000 + 7800
        Integer reduce1 = personList.stream().map(Person::getSalary).reduce(0, (x, y) -> x + y - 5000);
        //对每一部分都执行一次( 8900  + 7000 -5000 )+ 7800 -5000
        /**
         * 因为初始的是0 先执行中间的累加器
         * （0 + 8900） + （0 + 7000） + （0 + 7800） 每次累加都要把0带上 至此 执行第三个参数
         *  （0 + 8900） + （0 + 7000） -5000 + （0 + 7800） -5000
         *
         * 1.传入8900 7000 然后执行(x, y) -> x + y - 5000      8900 + 7000 -5000
         * 2.传入( 8900  + 7000 -5000 ) 和 7800              ( 8900  + 7000 -5000 ) + 7800 - 5000
         * 3.没了
         */
        Integer reduce2 = personList.parallelStream().map(Person::getSalary).reduce(0, (x, y) -> x + y, (x, y) -> x + y - 5000);
        System.out.println(reduce1);
        System.out.println(reduce2);

        Optional<Integer> reduce = personList.parallelStream().map(x -> {
            x.setSalary(x.getSalary() - 5000);
            return x.getSalary();
        }).reduce(Integer::sum);

    }

    /**
     * sorted()：自然排序，流中元素需实现Comparable接口
     * sorted(Comparator com)：Comparator排序器自定义排序
     */
    @Test
    void test06() {
        List<Person> personList = new ArrayList<Person>();

        personList.add(new Person("Sherry", 9000, 24, "female", "New York"));
        personList.add(new Person("Tom", 8900, 22, "male", "Washington"));
        personList.add(new Person("Jack", 9000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 8800, 26, "male", "New York"));
        personList.add(new Person("Alisa", 9000, 26, "female", "New York"));

        //工资升序
        List<Person> newList = personList.stream().sorted(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getSalary() - o2.getSalary();
            }
        }).collect(Collectors.toList());
        //工资降序 Comparator.comparing(Person::getSalary 不过是简化了new 比较器的过程
        List<String> newList2 = personList.stream().sorted(Comparator.comparing(Person::getSalary).reversed())
                .map(Person::getName).collect(Collectors.toList());
        //先年龄再工资
        List<String> newList3 = personList.stream()
                .sorted(Comparator.comparing(Person::getSalary).thenComparing(Person::getAge)).map(Person::getName)
                .collect(Collectors.toList());
        // 先按工资再按年龄自定义排序（降序）
        List<String> newList4 = personList.stream().sorted((p1, p2) -> {
            if (p1.getSalary() == p2.getSalary()) {
                return p2.getAge() - p1.getAge();
            } else {
                return p2.getSalary() - p1.getSalary();
            }
        }).map(Person::getName).collect(Collectors.toList());
        //Integer::compareTo 比较两个Interger 如果升序 返回1
        //personList.sort(Integer::compareTo); 如果personList是<integer>就可以了
        System.out.println("按工资升序排序：" + newList);
        System.out.println("按工资降序排序：" + newList2);
        System.out.println("先按工资再按年龄升序排序：" + newList3);
        System.out.println("先按工资再按年龄自定义降序排序：" + newList4);

    }

    /**
     * 合并、去重、限制、跳过
     */
    @Test
    void test07() {
        String[] arr1 = {"a", "b", "c", "d"};
        String[] arr2 = {"d", "e", "f", "g"};

        Stream<String> stream1 = Stream.of(arr1);
        Stream<String> stream2 = Stream.of(arr2);
        // concat:合并两个流 distinct：去重
        List<String> newList = Stream.concat(stream1, stream2).distinct().collect(Collectors.toList());
        // limit：限制从流中获得前n个数据  奇数流 1开始 1 3 5 7 9  取前十个
        List<Integer> collect = Stream.iterate(1, x -> x + 2).limit(10).collect(Collectors.toList());
        // skip：跳过前n个数据
        List<Integer> collect2 = Stream.iterate(1, x -> x + 2).skip(1).limit(5).collect(Collectors.toList());
        //斐波那契数列
        Stream.iterate(new int[]{0, 1}, n -> new int[]{n[1], n[0] + n[1]})
                .limit(20)
                .map(n -> n[0])
                .forEach(x -> System.out.println(x));

        System.out.println("流合并：" + newList);
        System.out.println("limit：" + collect);
        System.out.println("skip：" + collect2);

    }

    /**
     *
     */
    @Test
    void test08() {

        List<Person> personList = new ArrayList<Person>();

        personList.add(new Person("Sherry", 9000, 24, "female", "New York"));
        personList.add(new Person("Tom", 8900, 22, "male", "Washington"));
        personList.add(new Person("Jack", 9000, 25, "male", "Washington"));
        personList.add(new Person("Sherry", 8800, 26, "male", "New York"));
        personList.add(new Person("Sherry", 9000, 26, "female", "New York"));

        Stream<Integer> integerStream = personList.stream().map(x -> Integer.valueOf(x.getSalary()));
        /**
         * Collectors.toCollection:用自定义的数据结构来收集
         * Set 只要存的是对象 必须指定比较器不然不知道用哪个去重
         * ↓↓↓↓ 用Salary来当成去重的条件
         */
        TreeSet<Person> people = new TreeSet<>(Comparator.comparing(Person::getSalary));
        //如果名字相等 用年龄来判断是否重复 不然就按照名字来看是否重复
        TreeSet<Person> people2 = new TreeSet<>(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                if (o1.getName().equals(o2.getName())) {
                    return o1.getAge() - o2.getAge();
                }
                return o1.getName().equals(o2.getName()) ? 1 : -1;
            }
        });
        people2.add(new Person("Sherry", 9000, 124, "female", "New York"));
        people2.add(new Person("Sherry", 9000, 124, "female", "New York"));
        people2.add(new Person("Sherry2", 29000, 124, "female", "New York"));
        people2.stream().forEach(System.out::println);

        //去重
        ArrayList<Person> collect = personList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
//                return o1.getSalary() - o2.getSalary();
                return o1.getSalary().compareTo(o2.getSalary());
            }
        })), ArrayList::new));
        System.out.println("去重后：" + collect);
    }

    /**
     * 关于int和integer的互相转换，因为数组是int的，当我们想操作int数组的时候会用到
     */
    @Test
    void test09() {
        //boxed():int->integer
        //mapToInt:需要一个函数式接口，Integer的intValue方法就行，能吧integer->int
        Arrays.stream(new int[]{1, 2, 3, 4}, 0, 3).boxed().mapToInt(Integer::intValue).toArray();
    }

}
