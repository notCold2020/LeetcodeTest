package com.cxr.other.strangeDemo.lazyDemo;

public class User {

    // 用户 id
    private Long uid;
    // 用户的部门，为了保持示例简单，这里就用普通的字符串
    // 需要远程调用 通讯录系统 获得
    private Lazy<String> department;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getDepartment(Long uid) {
        return department.get();
    }

    /**
     * 因为 department 是一个惰性加载的属性，所以 set 方法必须传入计算函数，而不是具体值
     */
    public void setDepartment(Lazy<String> department) {
        this.department = department;
    }
    // ... 后面类似的省略
}
