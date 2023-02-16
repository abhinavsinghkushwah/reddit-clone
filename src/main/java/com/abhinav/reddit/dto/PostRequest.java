package com.abhinav.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {

	public String postName;
	public Long postId;
	public String subredditName;
	public String url;
	public String description;
	
}
