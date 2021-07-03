package com.cxr.other.permissionDemo.byRBAC;

import com.cxr.other.demo.entriy.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
public class UserController {

    // 由于要查询用户权限，五张表都来了
//    @Autowired
//    private UserMapper userMapper;
//    @Autowired
//    private RoleMapper roleMapper;
//    @Autowired
//    private UserRoleMapper userRoleMapper;
//    @Autowired
//    private RolePermissionMapper rolePermissionMapper;
//    @Autowired
//    private PermissionMapper permissionMapper;

    private HttpSession session;

    /**
     * 登录接口，登录的时候就把信息存进去
     * 信息：当前用户能访问的接口
     */
    @PostMapping("/login")
    public void login(@RequestBody User loginInfo) {

        // 1.Session记录登录状态  用户登录信息
        session.setAttribute(WebConstant.CURRENT_USER_IN_SESSION, new User(1, "这是从数据库里面查询出来的用户信息"));
        // 2.Session缓存用户拥有的权限
        session.setAttribute(WebConstant.USER_PERMISSIONS, getUserPermissions(123L));

    }

    private Object getUserPermissions(Long uid) {
        // 用户拥有的角色

        // 角色拥有的权限

        // 查询权限对应的method，放入set Set<String>

        return null;
    }
}
