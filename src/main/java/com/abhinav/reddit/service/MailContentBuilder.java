package com.abhinav.reddit.service;

import org.springframework.stereotype.Service; 
import org.thymeleaf.TemplateEngine;


import lombok.AllArgsConstructor;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class MailContentBuilder {
	private final TemplateEngine templateEngine;
	
	String build(String message) {
		Context context= new Context();
		context.setVariable("message", message);
		return templateEngine.process("mailTemplate", context);
	}
}
