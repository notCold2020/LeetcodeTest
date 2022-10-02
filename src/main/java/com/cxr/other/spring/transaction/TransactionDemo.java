package com.cxr.other.spring.transaction;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Random;
import java.util.UUID;

/**
 * @Author: CiXingrui
 * @Create: 2021/10/15 10:59 上午
 */
@Component
@RestController
public class TransactionDemo {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TransactionDemo transactionDemo;

    public TransactionDemo() {
        System.out.println();
    }

    /**
     * 经典例子 - private方法就算有事务注解也会失效，因为无法在外部调用到当前方法
     */
    @Transactional
    @GetMapping("/testTransaction1")
    private void testTransaction1(@Param("ii") int i) {
        testTransaction(i);
    }


    public void testTransaction(int i) {

        UserTransaction userTransaction = new UserTransaction();
        userTransaction.setUserName("张三");
        userTransaction.setGuid(i);
        userTransaction.setAccount(20000);

        userMapper.insert(userTransaction);
        int i1 = 1 / 0;

    }


    /**
     * 外层public,但是内部调用了private方法
     * 可以生效
     */
    @Transactional
    public void testTransaction2(int i) {
        testTransactionMapper2(i);
    }

    private void testTransactionMapper2(int i) {

        UserTransaction userTransaction = new UserTransaction();
        userTransaction.setUserName("张三");
        userTransaction.setGuid(i);
        userTransaction.setAccount(20000);

        userMapper.insert(userTransaction);
        int i1 = 1 / 0;

    }

    /**
     * 外层public,但是内部调用了private final方法
     * 依旧可以生效
     * 当然外层final的话直接编译期报错
     */
    @Transactional
    public void testTransaction3(int i) {
        testTransactionMapper2(i);
    }

    private final void testTransactionMapper3(int i) {

        UserTransaction userTransaction = new UserTransaction();
        userTransaction.setUserName("张三");
        userTransaction.setGuid(i);
        userTransaction.setAccount(20000);

        userMapper.insert(userTransaction);
        int i1 = 1 / 0;
    }

    /**
     * 方法内部调用，外层无事务，内层有事务
     */
    public void testTransaction4(int i) {
        /**
         * 解决方法就是让注入代理对象，然后把下面这行代码改成
         * transactionDemo.testTransactionMapper2(i);
         */
        testTransactionMapper2(i);
    }

    @Transactional
    public void testTransactionMapper4(int i) {

        UserTransaction userTransaction = new UserTransaction();
        userTransaction.setUserName("张三");
        userTransaction.setGuid(i);
        userTransaction.setAccount(20000);

        userMapper.insert(userTransaction);
        int i1 = 1 / 0;
    }


    /**
     * 方法内部开个线程
     * 会失效
     */
    @Transactional
    public void testTransaction5(int i) {
        new Thread(() -> {
            testTransactionMapper2(i);
            int ii = 1 / 0;
        }).start();
    }

    public void testTransactionMapper5(int i) {

        UserTransaction userTransaction = new UserTransaction();
        userTransaction.setUserName("张三");
        userTransaction.setGuid(i);
        userTransaction.setAccount(20000);

        userMapper.insert(userTransaction);
        int i1 = 1 / 0;
    }


    /**
     * 内部捕获了异常 没抛出 会失效
     */
    @Transactional
    public void testTransaction6(int i) {
        try {
            testTransactionMapper2(i);
            int ii = 1 / 0;
        } catch (Exception e) {

        }
    }

    public void testTransactionMapper6(int i) {

        UserTransaction userTransaction = new UserTransaction();
        userTransaction.setUserName("张三");
        userTransaction.setGuid(i);
        userTransaction.setAccount(20000);

        userMapper.insert(userTransaction);
        int i1 = 1 / 0;
    }


    /**
     * 自己抛出了错误的异常
     *
     * @Transaction默认只能回滚runtimeException和error 改成 @Transactional(rollbackFor = Exception.class)就好了
     */
    @Transactional
    public void testTransaction7(int i) throws Exception {
        try {
            testTransactionMapper2(i);
            int ii = 1 / 0;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public void testTransactionMapper7(int i) {

        UserTransaction userTransaction = new UserTransaction();
        userTransaction.setUserName("张三" + i);
        userTransaction.setGuid(i);
        userTransaction.setAccount(20000 + i);

        userMapper.insert(userTransaction);
//        int i1 = 1 / 0;
    }


    public void test(int i) {

        UserInfoDemo userInfoDemo = new UserInfoDemo();
        userInfoDemo.setGuid(UUID.randomUUID().toString().replaceAll("-","").substring(0,17));
        userInfoDemo.setUserName("张三"+i);
        userInfoDemo.setAge(new Random().nextInt(20));
        userInfoDemo.setAccount(i);
        userInfoDemo.setEmail(String.valueOf(new Random().nextInt(9)) + String.valueOf(new Random().nextInt(9)) + String.valueOf(new Random().nextInt(9)) + String.valueOf(new Random().nextInt(9)) + String.valueOf(new Random().nextInt(9)) + String.valueOf(new Random().nextInt(9)) + String.valueOf(new Random().nextInt(9)) + String.valueOf(new Random().nextInt(9)) + String.valueOf(new Random().nextInt(9)) + "@163.com");
        userInfoDemo.setCreateTime(new Timestamp(System.currentTimeMillis() - new Random().nextInt(2000000)));
        if(i%3==0){
            userInfoDemo.setCity("beijing");
        }else{
            userInfoDemo.setCity("shanghai");
        }

        userMapper.insertUserInfo(userInfoDemo);
//        int i1 = 1 / 0;
    }

}
