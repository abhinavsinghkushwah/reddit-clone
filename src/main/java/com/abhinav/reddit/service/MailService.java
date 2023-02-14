package com.abhinav.reddit.service;


import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.abhinav.reddit.exceptions.SpringRedditException;
import com.abhinav.reddit.model.NotificationEmail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@AllArgsConstructor
@Slf4j
public class MailService {
	private final JavaMailSender mailSender;
	private final MailContentBuilder mailContentbuilder;
	
	public void sendMail(NotificationEmail notificationEmail) {
		MimeMessagePreparator messagePerparator= mimeMessage ->{
			MimeMessageHelper messageHelper= new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("springreddit@email.com");
			messageHelper.setTo(notificationEmail.getRecipient());
			messageHelper.setSubject(notificationEmail.getSubject());
			messageHelper.setText(mailContentbuilder.build(notificationEmail.getBody()));
			
		};
		try {
			mailSender.send(messagePerparator);
			log.info("Activation email sent.");
		} catch(MailException e) {
			throw new SpringRedditException("Exception occured while sending mail");
		}
	}
	
}
