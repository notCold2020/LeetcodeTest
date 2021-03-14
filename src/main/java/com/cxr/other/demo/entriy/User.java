package com.cxr.other.demo.entriy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable,Cloneable {
    private Integer id;
    private String userName;
//    private String pwdddd;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
