package com.cxr.designpatterns.RulesEngine;

/**
 * @Date 2022/5/9 9:56 下午
 * @Created by CiXingrui
 */
public enum FastivalEnum {


    儿童节(1),

    父亲节(2);


    private Integer code;

    FastivalEnum(int i) {
        this.code = i;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
