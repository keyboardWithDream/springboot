package com.study.amqp.service;

import com.study.amqp.domain.UserInfo;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @Author Harlan
 * @Date 2020/10/13
 */
@Service
public class UserService {

    @RabbitListener(queues = {"study.news"})
    public void receive(UserInfo userInfo){
        System.out.println("收到消息: " + userInfo);
    }

    @RabbitListener(queues = {"study"})
    public void receive2(Message message){
        System.out.println(message.getBody());
        System.out.println(message.getMessageProperties());
    }
}
