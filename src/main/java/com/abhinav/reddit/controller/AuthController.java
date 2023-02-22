package com.abhinav.reddit.controller;

import static org.springframework.http.ResponseEntity.status;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhinav.reddit.dto.AuthenticationResponse;
import com.abhinav.reddit.dto.LoginRequest;
import com.abhinav.reddit.dto.PostResponse;
import com.abhinav.reddit.dto.RefreshTokenRequest;
import com.abhinav.reddit.dto.RegisterRequest;
import com.abhinav.reddit.service.AuthService;
import com.abhinav.reddit.service.PostService;
import com.abhinav.reddit.service.RefreshTokenService;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor	
public class AuthController {
	private final PostService postService;
	private final AuthService authService;
	private final RefreshTokenService refreshTokenService;
	
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody RegisterRequest reigsterRequest) {
		System.out.println("signup method reached");
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
		System.out.println("reached login backend");
		return authService.login(loginRequest);
	}
	
	@PostMapping("refresh/token")
	public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
		return authService.refreshToken(refreshTokenRequest);
	}
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted");
    }
	
}
