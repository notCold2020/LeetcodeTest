package com.cxr.other.permissionDemo.byRBAC;

/**
 * 普普通通的常量类，定义为抽象类也挺好
 */
public abstract class WebConstant {
    /**
     * 当前登录的用户信息（Session）
     */
    public static final String CURRENT_USER_IN_SESSION = "current_user_in_session";
    /**
     * 当前登录的用户信息（ThreadLocal<Map>）
     */
    public static final String USER_INFO = "user_info";
    /**
     * 当前用户拥有的权限
     */
    public static final String USER_PERMISSIONS = "user_permissions";
}
