package com.example.demo.socialmedia.repositories;

import com.example.demo.socialmedia.entities.Comment;
import com.example.demo.socialmedia.entities.Post;
import com.example.demo.socialmedia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {

    Comment findByUserAndPost(User user, Post post);
}
