package com.example.demo.socialmedia.controllers;


import com.example.demo.socialmedia.dtos.LikeDTO;
import com.example.demo.socialmedia.dtos.PostDTO;
import com.example.demo.socialmedia.entities.Like;
import com.example.demo.socialmedia.entities.Post;
import com.example.demo.socialmedia.entities.User;
import com.example.demo.socialmedia.services.LikeService;
import com.example.demo.socialmedia.services.PostService;
import com.example.demo.socialmedia.services.UserService;
import com.example.demo.socialmedia.utils.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LikeController {

    private LikeService likeService;

    private UserService userService;

    private PostService postService;

    @Autowired
    public LikeController(LikeService likeService, UserService userService, PostService postService) {
        this.likeService = likeService;
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("likes/{postid}")
    public int getTotalLike(@PathVariable int postid){
        return likeService.getTotalLikes(postid);
    }

    @PostMapping("{userid}/like/{postid}")
    public PostDTO likePost(@PathVariable String userid, @PathVariable int postid){

        User user = userService.findUserById(userid).get();
        Post post = postService.findPostById(postid).get();
        likeService.likePost(user,post);
        Post updatedpost = postService.findPostById(postid).get();
        return PostMapper.toPostDTO(updatedpost);
    }

    @DeleteMapping("{userid}/like/{postid}")
    public PostDTO unlikePost(@PathVariable String userid,@PathVariable int postid){

        User user = userService.findUserById(userid).get();
        Post post = postService.findPostById(postid).get();
        likeService.deleteLike(user,post);
        Post updatedPost = postService.findPostById(postid).get();
        return PostMapper.toPostDTO(updatedPost);
    }
}
