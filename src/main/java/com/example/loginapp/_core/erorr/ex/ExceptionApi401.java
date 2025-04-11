package com.example.loginapp._core.erorr.ex;

public class ExceptionApi401 extends RuntimeException {
    public ExceptionApi401(String message) {
        // ajax 요청
        super(message);
    }
} 