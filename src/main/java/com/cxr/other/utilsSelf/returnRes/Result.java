package com.cxr.other.utilsSelf.returnRes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private String data;
    private Integer code;

}
