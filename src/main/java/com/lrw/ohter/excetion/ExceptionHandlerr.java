package com.lrw.ohter.excetion;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerr {

    @ExceptionHandler(ExcetionSelf.class)
    public String ExceptionSelfHandler(Exception e){
        System.out.println("被我捕获到了");
        return "dddd";
    }

}
