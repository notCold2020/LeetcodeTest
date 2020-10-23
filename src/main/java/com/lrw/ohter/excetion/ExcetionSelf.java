package com.lrw.ohter.excetion;

import lombok.AllArgsConstructor;
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
