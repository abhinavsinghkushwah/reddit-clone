package com.abhinav.reddit.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubredditDto {

	private String description;
	private String name;
	private Long id;
	private Integer numberOfPosts;
}
