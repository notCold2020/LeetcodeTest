package com.cxr.other.spring.transaction;

import com.cxr.other.demo.entriy.User;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Date 2022/7/6 3:22 下午
 * @Created by CiXingrui
 */
@Data
public class UserInfoDemo {

    private String guid;
    private String userName;
    private Integer age;
    private Integer account;
    private String email;
    private Timestamp createTime;
    private String city;

    private User user;
}
