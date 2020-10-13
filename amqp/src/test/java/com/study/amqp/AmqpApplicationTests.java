package com.study.amqp;

import com.study.amqp.domain.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
class AmqpApplicationTests {

	@Autowired
	RabbitTemplate template;

	@Autowired
	AmqpAdmin amqpAdmin;

	@Test
	void contextLoads() {
		UserInfo userInfo = new UserInfo();
		userInfo.setId("001");
		userInfo.setUsername("陈晓龙");
		userInfo.setPassword("12345678");
		//对象被默认序列化后发送
		template.convertAndSend("exchaneg.fanout", "study.news", userInfo);
	}

	/**
	 * 接收消息
	 */
	@Test
	public void receive(){
		//接收数据
		Object o = template.receiveAndConvert("study.news");
		System.out.println(o.getClass());
		System.out.println(o);
	}


	/**
	 * 发送消息
	 */
	@Test
	public void sendMsg(){
		UserInfo userInfo = new UserInfo();
		userInfo.setId(UUID.randomUUID().toString().replaceAll("-",""));
		userInfo.setUsername("陈晓龙");
		userInfo.setPassword("12345678");
		template.convertAndSend("exchaneg.fanout","study",userInfo);
	}


	/**
	 * 创建交换器
	 */
	@Test
	public void createExchange(){
		amqpAdmin.declareExchange(new DirectExchange("exchange.amqpAdmin"));
	}

	/**
	 * 创建队列
	 */
	@Test
	public void createQueue(){
		amqpAdmin.declareQueue(new Queue("queue.amqpAdmin", true));
	}

	/**
	 * 绑定规则
	 */
	@Test
	public void creatBinding(){
		amqpAdmin.declareBinding(new Binding("queue.amqpAdmin", Binding.DestinationType.QUEUE, "exchange.amqpAdmin", "queue.#", null));
	}

}
