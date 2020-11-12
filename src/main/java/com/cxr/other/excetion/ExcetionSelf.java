package com.cxr.other.excetion;

import lombok.Data;

@Data
public class ExcetionSelf extends  RuntimeException {

    private String reason;
    private Integer code;

    public ExcetionSelf(String reason, Integer code) {
        this.reason = reason;
        this.code = code;
    }
}
