package com.study.task.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步任务
 * @Author Harlan
 * @Date 2020/10/14
 */
@Service
public class AsyncService {

    /**
     * 告诉Spring这是一个异步方法
     */
    @Async
    public void hello(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("处理数据中...");
    }
}
