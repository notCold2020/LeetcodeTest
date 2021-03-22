package com.cxr.other.java8.stream;

import com.cxr.other.demo.service.UserService;
import com.cxr.other.java8.Person;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
class TestDemoTest {

    Logger logger = LoggerFactory.getLogger(TestDemoTest.class);

    /**
     * Optional<T> 提供了null值检测，用来解决空指针异常
     * 咱们就不用写那么多if判断了
     */
    @Test
    void test0() {
        Integer value1 = null;
        Integer value2 = new Integer(10);

        // Optional.ofNullable - 允许传递为 null 参数
        Optional<Integer> a = Optional.ofNullable(value1);

        // Optional.of - 如果传递的参数是 null，抛出异常 NullPointerException
        Optional<Integer> b = Optional.of(value2);
        System.out.println(sum(a, b));
    }

    public Integer sum(Optional<Integer> a, Optional<Integer> b) {

        // Optional.isPresent - 判断值是否存在

        System.out.println("第一个参数值存在: " + a.isPresent());
        System.out.println("第二个参数值存在: " + b.isPresent());

        // Optional.orElse - 如果值存在，正常返回这个值，否则返回默认值 也就是0
        Integer value1 = a.orElse(new Integer(0));

        //Optional.get - 获取值，值需要存在
        Integer value2 = b.get();
        return value1 + value2;
    }

    /**
     * stream和parallelStream
     * 1.stream是顺序流 由主线程按照顺序对流进行顺序操作
     * 2.parallelStream  是并行流 在数据量大的时候 用这个并行处理 类似于开几个多线程批量插入sql 提高效率
     * 内部也就是用多线程的方式对流进行操作 然后合并到一起
     */
    @Test
    void test00() {
        List<Integer> list = Arrays.asList(7, 6, 9, 3, 8, 2, 1);
        Stream<Integer> stream = list.stream();
        Stream<Integer> integerStream = list.parallelStream();
        //这个方法转换成并行流
        Stream<Integer> parallel = list.stream().parallel();
    }


    /**
     * 遍历/匹配（foreach/find/match）
     * 注意：Stream中的元素是以Optional类存在的 用Optional接受
     */
    @Test
    void test01() {
        List<Integer> list = Arrays.asList(7, 6, 9, 3, 8, 2, 1);
        // 遍历输出符合条件的元素
        list.stream().filter(x -> x > 6).forEach(System.out::println);
        // 匹配第一个
        Optional<Integer> findFirst = list.stream().findFirst();
        // 任意匹配一个 每次都是一样的 类似于伪随机数
        Optional<Integer> findAny = list.stream().findAny();
        // 是否包含符合特定条件的元素
        boolean anyMatch = list.stream().anyMatch(x -> x < 6);
        boolean noneMatch = list.stream().noneMatch(x -> x < 6);
        System.out.println("匹配第一个值：" + findFirst.get());
        System.out.println("匹配任意一个值：" + findAny.get());
        System.out.println("是否存在大于6的值：" + anyMatch);
    }

    /**
     * filter 过滤
     */
    @Test
    void Test02() {
        /**
         *  传个断言型接口 返回Boolean值 x > 7 这不就是Boolean吗
         */
        List<Integer> list = Arrays.asList(6, 7, 3, 8, 1, 2, 9);
        list.stream().filter(x -> x > 7).forEach(System.out::println);

        /**----------------------------------------------------------------------------------------------------------------------------------------------------**/

        /**
         * 多条件过滤
         */
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 24, "female", "New York"));
        personList.add(new Person("Owen", 9500, 25, "male", "New York"));
        personList.add(new Person("Alisa", 7900, 26, "female", "New York"));
        personList.stream().filter(x -> x.getSalary() > 8000 && x.getAge() >= 25).forEach(System.out::println);

    }

    /**
     * 聚合（max/min/count)
     */
    @Test
    void Test03() {
        //最长的字符串 用Optional接收
        List<String> list1 = Arrays.asList("adnm", "admmt", "pot", "xbangd", "weoujgsd");
        Optional<String> max = list1.stream().max(Comparator.comparing(String::length));
        System.out.println(max.get());
        /**----------------------------------------------------------------------------------------------------------------------------------------------------**/
        //最大的Integer
        List<Integer> list2 = Arrays.asList(7, 6, 9, 4, 11, 6);
        //自然排序   ：： 的意思就是Integer类下的compareTo方法
        Optional<Integer> max1 = list2.stream().max(Integer::compareTo);
        //自定义排序
        Optional<Integer> max2 = list2.stream().max(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

        System.out.println("自然排序的最大值：" + max1.get());
        System.out.println("自定义逆序排序的最大值：" + max2.get());
        /**----------------------------------------------------------------------------------------------------------------------------------------------------**/

        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 9500, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
        personList.add(new Person("Anni", 7800, 24, "female", "New York"));
        personList.add(new Person("Owen", 9500, 25, "male", "New York"));
        personList.add(new Person("Alisa", 7800, 26, "female", "New York"));
        //ToIntFunction<T> 接收一个参数并返回int结果的函数
        Optional<Person> max3 = personList.stream().max(Comparator.comparingInt(Person::getSalary));
        System.out.println("员工工资最大值：" + max3.get().getSalary());
        /**----------------------------------------------------------------------------------------------------------------------------------------------------**/

        //多条件排序
        personList.stream().sorted(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                if ((o1.getSalary() - o2.getSalary()) != 0) {
                    return o1.getSalary() - o2.getSalary();
                } else {
                    return o1.getAge() - o2.getAge();
                }
            }
        }).forEach(System.out::println);


        /**----------------------------------------------------------------------------------------------------------------------------------------------------**/

        List<Integer> list = Arrays.asList(7, 6, 4, 8, 2, 11, 9);
        long count = list.stream().filter(x -> x > 6).count();
        System.out.println("list中大于6的元素个数：" + count);

    }

    /**
     * 映射(map/flatMap)
     */
    @Test
    void Test04() {
        /**
         * 1.map:接收一个函数作为参数，把这个函数作用到每个元素上
         * 2.flatMap:也是接收一个函数作为参数 将流中的每个值都换成另一个流
         * 然后把所有的流连接成一个新的流 有点类似于parallelStream并行流
         * 比如：现在有两箱鸡蛋 要变成煎蛋
         * map做的事情是把两箱鸡蛋变成煎蛋 依旧还在两个箱子里面
         * flatMap做的事情是变成煎蛋 然后放到一起
         *
         */
        String[] strArr = {"abcd", "bcdd", "defde", "fTr"};
        List<String> collect = Arrays.stream(strArr).map(String::toUpperCase).collect(Collectors.toList());
        List<String> collect1 = Arrays.stream(strArr).map(x -> x + 3).collect(Collectors.toList());

        System.out.println("每个元素大写：" + collect);
        System.out.println("每个元素+3：" + collect1);

        /**----------------------------------------------------------------------------------------------------------------------------------------------------**/
        //把员工的薪资全都 + 1000
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 24, "female", "New York"));
        personList.add(new Person("Owen", 9500, 25, "male", "New York"));
        personList.add(new Person("Alisa", 7900, 26, "female", "New York"));
        //注意return的东西 如果是return personNew , 那么用List<Person>来接收
        List<Integer> collect2 = personList.stream().map(person -> {
            Person personNew = new Person(person.getName(), 0, 0, null, null);
            personNew.setSalary(person.getSalary() + 10000);
            return personNew.getSalary();
        }).collect(Collectors.toList());
        System.out.println("collect2" + collect2);

        List<Person> collect3 = personList.stream().map(person -> {
            person.setSalary(person.getSalary() + 10000);
            return person;
        }).collect(Collectors.toList());
        System.out.println("collect3" + collect3);

        /**----------------------------------------------------------------------------------------------------------------------------------------------------**/
        //两个字符数组合并成一个新的数组
        List<String> list = Arrays.asList("m,k,l,a", "1,3,5,7");

        //字符串变成了数组
        List<String[]> collect5 = list.stream().map(o -> {
            String[] split = o.split(",");
            return split;
        }).collect(Collectors.toList());

        collect5.stream().forEach(o -> {
            for (String s : o) {
                System.out.println(s);
            }
        });

        //
        Stream<String> stringStream = list.stream().flatMap(s -> {
            String[] split = s.split(",");

            for (String s1 : split) {
                System.out.print("我是split:" + s1);
                System.out.println("---");
            }
            System.out.println("++");
            // 将每个元素转换成一个stream 想把两个数组拼装起来 肯定是需要用流来拼装的 所以才有了下面这一行的代码
            //个人理解：只要用了faltMap 就得需要变成流然后拼起来 不然就编译时异常
            Stream<String> s2 = Arrays.stream(split);
            return s2;
        });
        List<String> collect4 = stringStream.collect(Collectors.toList());

        System.out.println("处理前的集合：" + list);
        System.out.println("处理后的集合：" + collect4 + "处理后的集合长度" + collect4.size());
        //这个例子仔细看看 挺好的
        Optional<String> reduce = collect4.stream().reduce((x, y) -> {
            //我们对于每个字母 都是一样的操作 我们想缩减成一个东西 所以用reduce 每次的操作都是拼接 也就是 +
            return x + y;
        });
        System.out.println("6666666666:" + reduce.get());
    }

    /**
     * 归约(reduce)   又叫缩减  把一个流缩减成一个值 对其进行求和 乘积 最值
     */
    @Test
    void Test05() {
        List<Integer> list = Arrays.asList(2, 3, 2, 8, 11, 4);
        //BinaryOperator<T>  -----  操作员
        //提供两个参数，并且返回结果与输入参数类型一致的结果
        Optional<Integer> sum = list.stream().reduce((x, y) -> x + y); // 每次都是一样的操作
        Optional<Integer> sum2 = list.stream().reduce(Integer::sum);
        //第一个参数是累加器的初始值
        Integer sum3 = list.stream().reduce(11, Integer::sum);
        System.out.println("list求和：" + sum.get() + "," + sum2.get() + "," + sum3);

        /**----------------------------------------------------------------------------------------------------------------------------------------------------**/
        //乘积
        Optional<Integer> product = list.stream().reduce((x, y) -> x * y);
        System.out.println("list求积：" + product.get());
        /**----------------------------------------------------------------------------------------------------------------------------------------------------**/

        Optional<Integer> max1 = list.stream().max(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        System.out.println("我是自己写的最大值：" + max1.get());

        //最大值方式1
        Optional<Integer> max = list.stream().reduce((x, y) -> x > y ? x : y);
        //最大值方式2 虽然有操作员 但是sum函数就是需要两个参数返回一个参数
        Optional<Integer> max2 = list.stream().reduce(Integer::max);
        //最大值方式3  累加器的初始值就相当于 原来是1,2,3 现在有了初始值6 就是6,1,2,3 比较的时候就是61比较胜出者是6  62比较 balalalal
        Integer reduce = list.stream().reduce(2, (x, y) -> x > y ? x : y);
        System.out.println("list求max：" + max.get() + "," + max2.get());
        /**----------------------------------------------------------------------------------------------------------------------------------------------------**/
        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 25, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 21, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 24, "female", "New York"));
        personList.add(new Person("Owen", 9500, 25, "male", "New York"));
        personList.add(new Person("Alisa", 7900, 26, "female", "New York"));
        //工资之和方式1
        Integer integer = personList.stream().map(Person::getSalary).reduce(Integer::sum).get();
        /**
         * 第三个参数在于并行计算下 合并各个线程的计算结果
         * 多线程时,多个线程同时参与运算
         * 多个线程执行任务,必然会产生多个结果
         * 那么如何将他们进行正确的合并
         * 这就是第三个参数的作用   也类似与一个函数罢了
         */
        //工资之和方式2
        Integer sumSalary2 = personList.parallelStream().reduce(0, (sumsum, p) -> sumsum += p.getSalary(),
                (sum1, SSUM) -> sum1 + SSUM);
        //工资之和方式3
        Integer reduce1 = personList.stream().reduce(0, (ss, p) -> ss += p.getSalary(), Integer::sum);
        System.out.println("工资之和：" + integer + "," + sumSalary2 + "," + reduce1);
        /**----------------------------------------------------------------------------------------------------------------------------------------------------**/
        //  ((((5+1)+2)+3)+4)+5
        System.out.println(Arrays.asList(1, 2, 3, 4, 5).stream().reduce(5, (x, y) -> x + y, (x, y) -> x + y));
        //   (5+1)+ (5+2)+ (5+3)+ (5+4)+ (5+5)
        // 然后对上面的5个扩号里面的数字 执行第三个参数
        // (5+1) + (5+2) -1     +     8 -1 + 9 -1 +10 -1  上一个的结果作为现在的参数
        System.out.println("##" + Arrays.asList(1, 2, 3, 4, 5).parallelStream().reduce(5, (x, y) -> x + y, (x, y) -> x + y -1));
        System.out.println(Arrays.asList(1, 2, 3, 4, 5).stream().reduce(0, (x, y) -> x + y, (x, y) -> x + y));
        System.out.println(Arrays.asList(1, 2, 3, 4, 5).parallelStream().reduce(0, (x, y) -> x + y, (x, y) -> x + y));
        // 求最高工资方式1：
        Integer maxSalary = personList.stream().reduce(0, (MAX, p) -> MAX > p.getSalary() ? MAX : p.getSalary(),
                Integer::max);
        // 求最高工资方式2：
        // 初始值 : 顾名思义
        // 累加器 : 对每个 都执行这里面的操作 个人感觉类似于map
        // 组合器 : 只有在并行流中才执行 合并各个线程的计算结果
        Integer maxSalary2 = personList.stream().reduce(0, (MAXMAX, p) -> MAXMAX > p.getSalary() ? MAXMAX : p.getSalary(),
                (MAX1, MAX2) -> MAX1 > MAX2 ? MAX1 : MAX2);
        //求最高工资方式3
        Optional<Integer> max3 = personList.stream().map(x -> x.getSalary()).max(Integer::compareTo);
        System.out.println("最高工资：" + maxSalary + "," + maxSalary2 + "," +max3.get());
    }
}
