package com.abhinav.reddit.service;

import java.time.Instant;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.abhinav.reddit.dto.RegisterRequest;
import com.abhinav.reddit.model.NotificationEmail;
import com.abhinav.reddit.model.User;
import com.abhinav.reddit.model.VerificationToken;
import com.abhinav.reddit.repository.UserRepository;
import com.abhinav.reddit.repository.VerificationTokenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor //autowires the dependencies using constructor method
public class AuthService {
	
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	private final MailService mailService;
	@Transactional
	public void signup(RegisterRequest registerRequest) {
		User user=new User();
		user.setUsername(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setCreated(Instant.now());
		user.setEnabled(false);
		
		userRepository.save(user);
		
		String token=generateVerificationToken(user);
	
		mailService.sendMail(new NotificationEmail("Please activate account", user.getEmail(), "thanks for signing up"+"click on below url to activate your account"+"http://localhost:8080/api/auth/accountVerification/"+token));
		
	}
	private String generateVerificationToken(User user) {
		//takes user as input generates 128bit value used for verification and store it in database
		String token= UUID.randomUUID().toString();
		VerificationToken verificationToken= new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		
		verificationTokenRepository.save(verificationToken);
		return token;
		
	}
	
	
}
