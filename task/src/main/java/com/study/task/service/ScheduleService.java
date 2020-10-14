package com.study.task.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @Author Harlan
 * @Date 2020/10/14
 */
@Service
public class ScheduleService {


    @Scheduled(cron = "* * * * * 0-6")
    public void hello() {
        System.out.println("正在执行定时任务...");

    }
}

