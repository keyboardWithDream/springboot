package com.study.web.exception;

/**
 * @Author Harlan
 * @Date 2020/10/7
 */
public class HelloException extends RuntimeException {

    public HelloException() {
        super("HelloError!");
    }
}
