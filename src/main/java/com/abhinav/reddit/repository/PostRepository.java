package com.abhinav.reddit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinav.reddit.model.Post;
import com.abhinav.reddit.model.Subreddit;
import com.abhinav.reddit.model.User;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}