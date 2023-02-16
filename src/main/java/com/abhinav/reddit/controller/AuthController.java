package com.abhinav.reddit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhinav.reddit.dto.AuthenticationResponse;
import com.abhinav.reddit.dto.LoginRequest;
import com.abhinav.reddit.dto.RegisterRequest;
import com.abhinav.reddit.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor	
public class AuthController {
	
	private final AuthService authService;
	
	
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody RegisterRequest reigsterRequest) {
		authService.signup(reigsterRequest);
		return new ResponseEntity<>("User registration Successful",HttpStatus.OK);
	}
	@GetMapping("accountVerificationToken/{token}")
	public ResponseEntity<String> verifyAccount(@PathVariable String token){
		System.out.println("verifyaccount reached");
		authService.verifyAccount(token);
		return new ResponseEntity<>("Account activated", HttpStatus.OK);
	}
	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
		return authService.login(loginRequest);
	}
}
