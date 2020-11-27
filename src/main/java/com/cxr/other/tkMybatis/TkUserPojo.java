package com.cxr.other.tkMybatis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.LogicDelete;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tk_user")
public class TkUserPojo {
    @Id
    private Long id;
    private String name;
    private Integer age;
    private Date createTime;
    private Date updateTime;

    @LogicDelete()
    /**
     * 注意此处的包装类型
     */
    private Boolean deleted;
}
