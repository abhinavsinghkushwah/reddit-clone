package com.abhinav.reddit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class RedditSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditSpringApplication.class, args);
	}

}
