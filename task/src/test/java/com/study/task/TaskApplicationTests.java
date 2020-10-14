package com.study.task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@SpringBootTest
class TaskApplicationTests {

	@Autowired
	JavaMailSender mailSender;

	@Test
	void contextLoads() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setSubject("通知: 这是一个邮件测试!");
		message.setText("您好!彭小川先生,这是一个SpringBoot的邮件任务测试,收到后请勿回复,谢谢!");
		message.setTo("765403293@qq.com");
		message.setFrom("i102443@163.com");
		mailSender.send(message);
	}


	/**
	 * 高级邮件
	 * @throws MessagingException 异常
	 */
	@Test
	public void sendMail() throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
		messageHelper.setSubject("通知: 这是一个高级邮件测试!");
		messageHelper.setText("<h1>这是一个高级邮件测试!</h1>", true);
		messageHelper.setTo("765403293@qq.com");
		messageHelper.setFrom("i102443@163.com");
		messageHelper.addAttachment("test.png", new File("C:/Users/13536/Desktop/kanye west.png"));
		mailSender.send(message);
	}
}
