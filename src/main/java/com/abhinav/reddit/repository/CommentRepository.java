package com.abhinav.reddit.repository;


import org.springframework.data.jpa.repository.JpaRepository; 


import com.abhinav.reddit.model.Comment;
import com.abhinav.reddit.model.Post;
import com.abhinav.reddit.model.User;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}