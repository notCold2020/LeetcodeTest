package com.cxr.other.demo.dao;

import com.cxr.other.demo.entriy.Teacher;
import com.cxr.other.demo.entriy.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class userDaoTest {

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
        logger.info(userByName+"---");
    }

    @Test
    void insertUser(){
//        User user = new User();
//        user.setUserName("插入测试");
//        user.setPwd("测试密码");
//        userDao.insertUser(user);
    }

    @Test
    void updateuser(){
        userDao.updateUserByid("1","大狗子");
    }

    @Test
    void deleteByid(){
        userDao.deleteUserById(5);
    }

    @Test
    void insertByMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("id",11);
        map.put("userName",1);
        map.put("password",1);

        userDao.insert(map);
    }

    @Test
    void selectUser(){
        List<User> users = userDao.selectUser("张三");
        for(User u1:users){
            System.out.println(u1);
        }
    }

    @Test
    void selectByLikeTest(){
        List<User> users = userDao.selectByLike("张");
        for(User u1:users){
            System.out.println(u1);
        }
    }

    @Test
    void selectStudent(){
        List<Teacher> students = userDao.selectTeacher2();
        for(Teacher student:students){
            System.out.println(student);
        }
    }

    @Test
    void getUserListTest(){
        List<User> userList = userDao.getUserList(Arrays.asList(1, 2, 3, 4));
        for(User user:userList){
            System.out.println(user);
        }
    }

    @Test
    void getUserMapTest(){
        List<Map<String, Object>> userMap = userDao.getUserMap();
        for(Map<String, Object> user:userMap){
            System.out.println(user);
        }
    }

    @Test
    void insertListTest(){
        List<User> list = new ArrayList<>();
        list.add(new User(222,"222","222"));
        list.add(new User(2272,"222","222"));
        list.add(new User(22772,"222","222"));
        userDao.insertList(list);
    }

    @Test
    void getListMapTest(){
        Map<String, Object> listMap = userDao.getListMap();
        System.out.println(listMap);
    }
}
