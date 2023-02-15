package com.abhinav.reddit.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.abhinav.reddit.dto.AuthenticationResponse;
import com.abhinav.reddit.dto.LoginRequest;
import com.abhinav.reddit.dto.RegisterRequest;
import com.abhinav.reddit.exceptions.SpringRedditException;
import com.abhinav.reddit.model.NotificationEmail;
import com.abhinav.reddit.model.User;
import com.abhinav.reddit.model.VerificationToken;
import com.abhinav.reddit.repository.UserRepository;
import com.abhinav.reddit.repository.VerificationTokenRepository;
import com.abhinav.reddit.security.JwtProvider;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor //autowires the dependencies using constructor method
public class AuthService {
	private final JwtProvider jwtProvider;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	private final MailService mailService;
	private final AuthenticationManager authenticationManager;
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
		System.out.println("main function token "+ token);
		mailService.sendMail(new NotificationEmail("Please activate account", user.getEmail(), "thanks for signing up"+"click on below url to activate your account"+"http://localhost:8080/api/auth/accountVerificationToken/"+token));
		
	}
	private String generateVerificationToken(User user) {
		//takes user as input generates 128bit value used for verification and store it in database
		String token= UUID.randomUUID().toString();
		VerificationToken verificationToken= new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		System.out.println("helper function token "+ token);
		verificationTokenRepository.save(verificationToken);
		return token;
		
	}
	public void verifyAccount(String token) {
		System.out.println("verifyaccount step 1");
		Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
		verificationToken.orElseThrow(() -> new SpringRedditException("Invalid token"));
		System.out.println("verifyaccount step 2");
		fetchUserAndEnable(verificationToken.get());
	}
	@Transactional
	public void fetchUserAndEnable(VerificationToken verificationToken) {
		String username=verificationToken.getUser().getUsername();
		User user=userRepository.findByUsername(username).orElseThrow(()-> new SpringRedditException("user with name  "+ username+ " not found"));
		System.out.println("mil gya user");
		user.setEnabled(true);
		userRepository.save(user);
	}
	
	public AuthenticationResponse login(LoginRequest loginRequest) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		String token=jwtProvider.generateToken(authenticate);
		return new AuthenticationResponse(token, loginRequest.getUsername());	
		}
	
	
}
