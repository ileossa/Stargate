package com.ileossa.project;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.ileossa.project.service.email.MailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


/**
 * Created by v.lafosse on 27/07/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MailService.class)
public class MailTests {
//
//	@Resource
//	private JavaMailSenderImpl javaMailSender;
//
//	private GreenMail greenMail;
//
//	@Before
//	public void startUp(){
//		greenMail = new GreenMail(ServerSetupTest.SMTP);
//		greenMail.start();
//
//		javaMailSender.setJavaMailProperties();
//	}
//
//	@Test
//	public void init(){
//		greenMail =
//	}


}
