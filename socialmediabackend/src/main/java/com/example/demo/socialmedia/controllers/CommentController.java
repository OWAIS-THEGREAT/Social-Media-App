package com.example.demo.socialmedia.controllers;

import com.example.demo.socialmedia.dtos.CommentDTO;
import com.example.demo.socialmedia.dtos.PostDTO;
import com.example.demo.socialmedia.entities.Comment;
import com.example.demo.socialmedia.entities.Post;
import com.example.demo.socialmedia.entities.User;
import com.example.demo.socialmedia.services.CommentService;
import com.example.demo.socialmedia.services.PostService;
import com.example.demo.socialmedia.services.UserService;
import com.example.demo.socialmedia.utils.ApiResponse;
import com.example.demo.socialmedia.utils.CommentMapper;
import com.example.demo.socialmedia.utils.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    private UserService userService;

    private PostService postService;

    private CommentService commentService;

    @Autowired
    public CommentController(UserService userService, PostService postService, CommentService commentService) {
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
    }

    @PostMapping("{userid}/comment/{postid}")
    public PostDTO postComment(@PathVariable String userid, @PathVariable int postid, @RequestBody Comment comment){
        User user = userService.findUserById(userid).get();
        Post post  = postService.findPostById(postid).get();
        comment.setUser(user);
        comment.setPost(post);

        Comment tempComment = commentService.postComment(comment);
        CommentDTO commentDTO = CommentMapper.toCommentDTO(tempComment);

        Post updatedpost = postService.findPostById(postid).get();
        return PostMapper.toPostDTO(updatedpost);
    }

    @DeleteMapping("{userid}/comment/{postid}")
    public void deleteComment(@PathVariable String userid, @PathVariable int postid){
        User user = userService.findUserById(userid).get();
        Post post  = postService.findPostById(postid).get();
        commentService.deleteComment(user,post);
    }

}
