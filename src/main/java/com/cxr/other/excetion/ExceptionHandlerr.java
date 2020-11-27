package com.cxr.other.excetion;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerr {
    /**
     * 只要抛出ExcetionSelf 这个异常就会被这里捕获到
     * @param e
     * @return
     */
    @ExceptionHandler(ExcetionSelf.class)
    public String ExceptionSelfHandler(Exception e){
        System.out.println("被我捕获到了");
        return "dddd";
    }

}
