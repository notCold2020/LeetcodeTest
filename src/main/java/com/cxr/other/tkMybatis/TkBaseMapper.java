package com.cxr.other.tkMybatis;

import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

@RegisterMapper
public interface TkBaseMapper<T> extends Mapper<T>, BaseMapper<T>, IdListMapper<T,Long>, InsertListMapper<T>,MyBatchUpdateMapper<T> {
}
