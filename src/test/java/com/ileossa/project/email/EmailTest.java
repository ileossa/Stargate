package com.ileossa.project.email;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.ileossa.project.email.config.EmailConfig;
import com.ileossa.project.mail.EmailService;
import com.ileossa.project.mail.impl.EmailServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ileossa on 02/08/2017.
 */
@ContextConfiguration(classes = EmailConfig.class)
@SpringBootTest
@Import(EmailServiceImpl.class)
@RunWith(SpringRunner.class)
public class EmailTest {

    @Resource
    private JavaMailSenderImpl emailSender;
    @Autowired
    private EmailServiceImpl emailService;


    private GreenMail testSmtp;
    private String from = "test@sender.com";
    private String to = "test@receiver.com";
    private String subject = "test subject";
    private String text = "test message";

    @Before
    public void testSmtpInit(){
        testSmtp = new GreenMail(ServerSetupTest.SMTP);
        testSmtp.start();

        //don't forget to set the test port!
        emailSender.setPort(3025);
        emailSender.setHost("localhost");
    }

    @Test
    public void testEmail() throws InterruptedException, MessagingException {

        emailService.sendSimpleMessage(to, subject, text);

        assertTrue(testSmtp.waitForIncomingEmail(5000, 1));
        Message[] messages = testSmtp.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals("test subject", messages[0].getSubject());
        String body = GreenMailUtil.getBody(messages[0]).replaceAll("=\r?\n", "");
        assertEquals("test message", body);
    }

    @After
    public void cleanup(){
        testSmtp.stop();
    }
}


















