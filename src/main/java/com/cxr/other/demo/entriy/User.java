package com.cxr.other.demo.entriy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable,Cloneable {
    private Integer id;
    @JsonProperty("user_name")
    private String userName;
//    private String pwdddd;



    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
