package com.abhinav.reddit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhinav.reddit.dto.VoteDto;
import com.abhinav.reddit.service.VoteService;

import lombok.AllArgsConstructor;
import lombok.Data;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/votes")
@AllArgsConstructor
public class VoteController {

	private final VoteService voteService;
	
	@PostMapping
	public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto){
		voteService.vote(voteDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
