package com.cxr.other.tkMybatis;

import org.apache.ibatis.annotations.UpdateProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;

/**
 * 通用Mapper只提供了批量查询、批量插入、批量删除（物理）的接口，需要我们自定义批量更新的接口
 * 使用时记得在数据库连接URL后加上：allowMultiQueries=true（允许批量更新）
 * 比如
 * spring.datasource.username=root
 * spring.datasource.password=root
 * spring.datasource.url=jdbc:mysql:///:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true
 *
 * @author qiyu
 * @see IdListMapper 批量查询、批量删除（物理删除，慎用）
 * @see tk.mybatis.mapper.common.sqlserver.InsertMapper 批量插入（全量插入，数据库默认值会失效）
 */
@RegisterMapper
public interface MyBatchUpdateMapper<T> {

    /**
     * 批量更新
     *
     * @param list
     */
    @UpdateProvider(type = MyBatchUpdateProvider.class, method = "dynamicSQL")
    void batchUpdate(List<T> list);

}
