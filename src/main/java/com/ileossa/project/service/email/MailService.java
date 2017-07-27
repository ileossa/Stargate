package com.ileossa.project.service.email;

import com.ileossa.project.MyAccessDeniedHandler;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.logging.Logger;


/**
 * Created by v.lafosse on 27/07/2017.
 */
public class MailService{

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(MailService.class);

	private final JavaMailSender javaMailSender;

	@Autowired
	public MailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void send( Email paramEmail){
		if( paramEmail.isHtml()){
			try {
				sendHtmlMail(paramEmail);
			} catch(MessagingException e) {
				logger.error("Cannot send email. \n" + e);
			}
		}else {
			try {
				sendPlainTextMail(paramEmail);
			} catch(MessagingException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Method to send email, with html content
	 * @param email
	 * @throws MessagingException
	 */
	private void sendHtmlMail(Email email) throws MessagingException {
		boolean isHtml = true;  			//default value true
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper descriptionMessage = new MimeMessageHelper(message);

		descriptionMessage.setTo(email.getTo().toArray(new String[email.getTo().size()]));
		descriptionMessage.setReplyTo(email.getFrom());
		descriptionMessage.setFrom(email.getFrom());
		descriptionMessage.setText(email.getMessage(), isHtml);
		descriptionMessage.setSubject(email.getSubject());
		if( email.getCc().size() > 0 ) {
			descriptionMessage.setCc(email.getCc().toArray(new String[email.getTo().size()]));
		}
		javaMailSender.send(message);
	}

	/**
	 * Method to send email, with text in content
 	 * @param email
	 * @throws MessagingException
	 */
	private void sendPlainTextMail(Email email) throws MessagingException {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

		simpleMailMessage.setTo(email.getTo().toArray(new String[email.getTo().size()]));
		simpleMailMessage.setReplyTo(email.getFrom());
		simpleMailMessage.setFrom(email.getFrom());
		simpleMailMessage.setText(email.getMessage());
		simpleMailMessage.setSubject(email.getSubject());
		if( email.getCc().size() > 0 ) {
			simpleMailMessage.setCc(email.getCc().toArray(new String[email.getTo().size()]));
		}
		javaMailSender.send(simpleMailMessage);
	}


}
