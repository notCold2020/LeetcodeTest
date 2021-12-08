package com.cxr.other.java8.stream;

import com.cxr.other.demo.entriy.User;

import java.util.Optional;

public class TestDemo {
    static User user;

    public static void main(String[] args) {
        Optional.ofNullable(user).ifPresent(x -> {
            System.out.println(x + "11");
        });

    }
}
