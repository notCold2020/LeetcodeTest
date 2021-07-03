package com.cxr.other.demo.dao;

import com.cxr.other.demo.entriy.Student;
import com.cxr.other.demo.entriy.Teacher;
import com.cxr.other.demo.entriy.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface userDao {
    //吧username赋给name属性 这个name就是xml里面的name 就是我们传给xml的参数
    List<User> getUser(@Param("name") String username, @Param("pwd") String password);

    User getUserByName(@Param("name") String username);

    String getStringUsername(Integer id);

    void insertUser(User user);

    void updateUserByid(@Param("id") String id, @Param("userNamee") String name);

    void deleteUserById(Integer iddd);//不用@Param，名字就叫iddd,可以点进去

    void insertByMap(Map<String, Object> mapp);//靠着 id="insertByMap" 才会有左侧这个红色的→箭头

    List<User> selectUser(String name);

    List<User> selectByLike(String mohu);

    List<Student> selectTeacher();//一个学生有一个老师

    List<Teacher> selectTeacher2();//一个老师有多个学生

    List<User> getUserList(List<Integer> list);

    List<Map<String, Object>> getUserMap();

    void insertList(List<User> users);

    List<Map<String, Object>> getListMap();
}
