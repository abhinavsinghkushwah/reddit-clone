package com.abhinav.reddit.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.abhinav.reddit.dto.PostRequest;
import com.abhinav.reddit.dto.PostResponse;
import com.abhinav.reddit.model.Post;
import com.abhinav.reddit.model.Subreddit;
import com.abhinav.reddit.model.User;
import com.abhinav.reddit.repository.CommentRepository;
import com.abhinav.reddit.repository.VoteRepository;
import com.abhinav.reddit.service.AuthService;

@Mapper(componentModel = "spring")
public interface PostMapper {

	//had to add a plugin with annotation processor paths which has mapstruct and 
	// lombok and mapstruct binding, otherwise mapper was not implementing this interface
	//https://stackoverflow.com/questions/38162941/unknown-property-in-a-return-type


    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "user", source = "user")
    Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "postName", source = "postName")
    PostResponse mapToDto(Post post);

    }