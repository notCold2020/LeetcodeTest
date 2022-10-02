package com.cxr.other.spring.transaction;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Date 2022/6/2 8:21 下午
 * @Created by CiXingrui
 */
@Mapper
public interface UserMapper {

    void insert(@Param("user") UserTransaction userTransaction);

    void insertUserInfo(@Param("user") UserInfoDemo userInfoDemo);

//    void update(UserTransaction userTransaction);

}
