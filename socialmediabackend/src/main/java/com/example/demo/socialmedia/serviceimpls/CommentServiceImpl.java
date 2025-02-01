package com.example.demo.socialmedia.serviceimpls;

import com.example.demo.socialmedia.entities.Comment;
import com.example.demo.socialmedia.entities.Post;
import com.example.demo.socialmedia.entities.User;
import com.example.demo.socialmedia.repositories.CommentRepository;
import com.example.demo.socialmedia.repositories.PostRepository;
import com.example.demo.socialmedia.repositories.UserRepository;
import com.example.demo.socialmedia.services.CommentService;
import com.example.demo.socialmedia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {


    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment postComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(User user, Post post) {
        Comment comment = commentRepository.findByUserAndPost(user, post);
        commentRepository.delete(comment);
    }

}
