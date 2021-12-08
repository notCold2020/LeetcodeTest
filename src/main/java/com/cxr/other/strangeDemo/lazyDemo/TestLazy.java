package com.cxr.other.strangeDemo.lazyDemo;

/**
 * @Author: CiXingrui
 * @Create: 2021/11/11 7:43 下午
 */
public class TestLazy {
    static User departmentService;

    public static void main(String s) {
        Long uid = 1L;
        User user = new User();
        user.setUid(uid);
        /**
         * departmentService 是一个rpc调用
         *
         */
        user.setDepartment(Lazy.of(() -> departmentService.getDepartment(uid)));

        /**
         * 只有执行get()方法的时候才会真正执行departmentService.getDepartment(uid)
         *
         * 如果直接把departmentService.getDepartment(uid)的结果setDepartment进内存
         * 就执行了rpc调用，还可能用不上
         */
        user.getDepartment(uid);
    }
}
