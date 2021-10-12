package com.cxr.other.demo.entriy;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Builder
@Data
public class User implements Serializable, Cloneable {
    private  Integer id;
    private String userName;
    private String pwdddd;
    @Singular
    private List<String> lls = new ArrayList<>();


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;

    public User(Integer id, String userName, String pwdddd) {
        this.id = id;
        this.userName = userName;
        this.pwdddd = pwdddd;
    }

    public User(Integer id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public Integer getId() {
        return id;

    }

}

