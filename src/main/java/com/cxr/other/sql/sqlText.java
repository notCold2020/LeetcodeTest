package com.cxr.other.sql;

/**
 * @Date 2022/7/7 7:59 下午
 * @Created by CiXingrui
 */
public class sqlText {

    /**
     *  关于limit
     *
     *  1.select user_name from t_user_info_demo where account = 1000000  limit 1 --  1.5s
     *  2.select user_name from t_user_info_demo where account = 4000000  limit 1 --  4s
     *  3.select user_name from t_user_info_demo where account = 1000000  --  5s
     *  4.select user_name from t_user_info_demo where account = 1000000  limit 5000000   -明明只有一条数据但是要limit 500w,也会慢
     *
     *  500w数据，account字段没有索引，走全表扫描，说明limit 1 确实是查询到一条了就不继续往下走了
     *  所以同样是全表扫描3情况扫了500w条，2情况扫了400w条 发现客户端就要1条就停止，1情况扫了100w条 发现就要一条就停止
     */

    /**
     * 深分页  account字段有普通索引
     * 前置条件就是 自增列有索引
     * 如果是项目中可以用时间字段来走索引获取到主键
     *
     * 1. select * from t_user_info_demo  order by account limit 4000000,5;  -- 10s
     *
     * 2. select * from t_user_info_demo where account >=
     *                                         (
     *                                          select  account from t_user_info_demo limit 4000000,1
     *                                          ) limit 5;     -- 1s
     * 3. select * from t_user_info_demo where account between 4000000 and 4000005
     *
     * 1.全表扫描，虽然order by account会用到account的索引，但是查询的是select * ,要回表4000000多次，mysql觉得不如全表扫描了
     * 如果把select * 换成select account，那就using index 用了索引覆盖
     * 2.
     * 1,PRIMARY,t_user_info_demo,,【range】,account_index,account_index,5,,2075200,100,【Using where】
     * 2,SUBQUERY,t_user_info_demo,,【index】,,account_index,5,,5579562,100,【Using index】
     *
     * 子查询用了索引覆盖，查询出来一条数据
     * 外层查询走了account索引，记得加上limit 5
     *
     * 3.更快
     *
     */


    /**
     * order by:
     * 先说结论：1.走索引
     * account字段有索引
     *
     * 1.select * from t_user_info_demo order by account 非常恐怖 全表扫描并且用到了文件排序
     *
     * 2.select account from t_user_info_demo order by account  全索引扫描，好点了。并且能用到索引下推 - 15s 但是500w数据还是很慢，因为磁盘排序
     *
     * 3.select account from t_user_info_demo order by account limit 500  0.5s
     *
     * 4.select * from t_user_info_demo order by account limit 500 全索引扫描，还是会走索引之后再回表
     */

    /**
     * 查询优化器
     * 这俩sql执行的时间都是一样的 优化器会自动优化where条件中的顺序
     * 1.select city from t_user_info_demo where   user_name = '张三4656994' and city = 'shanghai'
     * 2.select city from t_user_info_demo where   city = 'shanghai' and user_name = '张三4656994'
     */

}
