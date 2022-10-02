package com.cxr.other.demo.dao;

import com.cxr.other.demo.entriy.Student;
import com.cxr.other.demo.entriy.Teacher;
import com.cxr.other.demo.entriy.User;
import com.cxr.other.demo.service.UserService;
import com.cxr.other.spring.beanFactoryPostProcessorDemo.BeanFactoryPostProcessorTest;
import com.cxr.other.utilsSelf.ApplicationUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class userDaoTest {

    @Autowired
    private userDao userDao;

    static Logger logger = LoggerFactory.getLogger(userDaoTest.class);

    @Test
    void getUser() {

        List<User> user = userDao.getUser("张三", "123456");
        logger.info(user.toString());

    }

    @Test
    void getUserByName() {
        String userByName = userDao.getStringUsername(1);
        logger.info(userByName + "---");
    }

    @Test
    void insertUser() {
        User user = new User();
//        user.setPwdddd("123456");
        userDao.insertUser(user);
    }

    @Test
    void updateuser() {
        userDao.updateUserByid("1", "大狗子");
    }

    @Test
    void deleteByid() {
        userDao.deleteUserById(5);
    }

    @Test
    void insertByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 2); //map的key对应xml里面的#{id,jdbcType=VARCHAR }
        map.put("userName", "李四");
        map.put("password", "6666666");

        userDao.insertByMap(map);
    }

    @Test
    void selectUser() {
        List<User> users = userDao.selectUser("张三");
        for (User u1 : users) {
            /**
             * select id,user_name from user；
             * 没有查询的字段就是null呗
             * User(id=1, userName=张三, pwdddd=null, date=null)
             * User(id=3, userName=张三, pwdddd=null, date=null)
             */
            System.out.println(u1.getUserName());
        }
    }

    @Test
    void selectByLikeTest() {
        List<User> users = userDao.selectByLike("张");
        for (User u1 : users) {
            System.out.println(u1);
        }
    }

    @Test
    void selectTeacher() {
        /**
         * Student(id=1, name=张三, teacher=Teacher(id=0, name=张三老师, students=null))
         * Student(id=2, name=李四, teacher=Teacher(id=0, name=李四老师, students=null))
         *
         * <association property="teacher" javaType="com.cxr.other.demo.entriy.Teacher">
         *             <result property="name" column="tName"/> 再说一遍property只对应实体的属性名字
         * </association>
         */
        List<Student> students = userDao.selectTeacher();
        for (Student student : students) {
            System.out.println(student);
        }
    }

    @Test
    void selectTeacher2() {
        List<Teacher> teachers = userDao.selectTeacher2();
        for (Teacher teacher : teachers) {
            System.out.println(teacher);
        }
    }

    @Test
    void getUserListTest() {
        List<User> userList = userDao.getUserList(Arrays.asList(1, 2, 3, 4));
        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Test
    void getUserMapTest() {
        List<Map<String, Object>> userMap = userDao.getUserMap();
        for (Map<String, Object> user : userMap) {
            System.out.println(user);
        }
    }

    @Test
    void insertListTest() {
        List<User> list = new ArrayList<>();
        list.add(new User(222, "222", "222"));
        list.add(new User(2272, "222", "222"));
        list.add(new User(22772, "222", "222"));
        userDao.insertList(list);
    }

    @Test
    void getListMapTest() {
        List<Map<String, Object>> listMap = userDao.getListMap();
        System.out.println(listMap);
    }

    @Autowired
    private UserService userService1;

    @Autowired
    private UserService userService2;

    @Test
    void et() {
        BeanFactoryPostProcessorTest tForBeanFactoryPostProcessor = (BeanFactoryPostProcessorTest) ApplicationUtils.getBean("beanFactoryPostProcessorTest");
        String name = tForBeanFactoryPostProcessor.getName();
        System.out.println(name);
    }




}

