package com.cxr.other.java8.optional;

import com.alibaba.fastjson.JSON;
import com.cxr.other.demo.entriy.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SpringBootTest
class optionalTestTest {

    @Test
    void test0() {
        String userID = null;

        String res = Optional.ofNullable(userID)//要一个允许为null的参数
                .map(id -> "Map的返回值")
                .orElse("userId-orElseGet");//如果入参为空，那么就执行orElse里面的值作为返回值，因为我们map有返回值

        System.out.println(res);
    }

    @Test
    void test1() {
        String userID = null;
        String res = Optional.ofNullable(userID)
                .map(id -> "Map的返回值:" + id)
                .orElseGet(() -> {//这是个供给型接口，就是没有入参有返回值
                    /**
                     * 这里就可以做一些复杂的操作，比如进行埋点，而不是像orElse一样，
                     * 只能返回一个特定值。
                     */
                    User user = new User();

                    return "我是orElseGet的返回值";
                });
        System.out.println(res);
    }

    @Test
    void test2() throws InterruptedException {
        String userID = null;
        String res = Optional.ofNullable(userID)
                .map(id -> "Map的返回值:" + id)
                /**
                 * orElseThrow：只是对比于orElse报错
                 */
                .orElseThrow(() -> {
                    User user = new User();
                    return new InterruptedException();
                });
        System.out.println(res);
    }

    @Test
    void test3() {
        String userId = null;
        /**
         * Optional.of要的是个非null，如果传进来个null直接就NPE了。
         * 所以后面的orElseGet也用不上，因为是null直接就NPE了
         *
         */
        String res = Optional.of(userId).map(id -> "Map的返回值:" + id)
                .orElseGet(() -> {
                    User user = new User();

                    return "我是orElseGet的返回值";
                });

        System.out.println(res);
    }

    @Test
    void test4() {
        String userId = "123";

        List<User> lists = new ArrayList<>();
        /**
         * ifPresent 用于对过滤出的数据如果存在。如果经过过滤条件后，有数据的话就可以进行修改。
         * 如果.ifPresent()前面这一坨不为null(存在) 那么就执行后面的操作
         *
         * 那如果不存在呢？
         * 那就跳过不执行呗
         *
         * 所以map+orElse适合于如果我们相判断的值需要补偿方案的时候，我们用
         * ifPresent则是我们相判断的值是null就什么都不做 不care
         */
        queryUserById(userId).ifPresent(res -> {
            res.setUserName("userId");
            System.out.println("--ifPresent执行--");
            lists.add(res);
        });


        System.out.println(JSON.toJSONString(lists));
    }

    private Optional<User> queryUserById(String userId) {
        User user = new User();
//        user = dao.select();
        if (Objects.isNull(user)) {
            return Optional.ofNullable(user);
        }
        return Optional.empty();
    }
}