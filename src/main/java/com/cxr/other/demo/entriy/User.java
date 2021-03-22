package com.cxr.other.demo.entriy;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class User implements Serializable, Cloneable {
    private Integer id;
    private String userName;
    //    private String pwdddd;
    private Useruser useruser;

    public User(Integer id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Data
    @AllArgsConstructor
    public class Useruser{
        private String usus;
    }

}

