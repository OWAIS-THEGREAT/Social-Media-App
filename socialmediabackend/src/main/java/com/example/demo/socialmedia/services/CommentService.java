package com.example.demo.socialmedia.services;

import com.example.demo.socialmedia.entities.Comment;
import com.example.demo.socialmedia.entities.Post;
import com.example.demo.socialmedia.entities.User;

public interface CommentService {

    Comment postComment(Comment comment);

    void deleteComment(User user, Post post);
}
