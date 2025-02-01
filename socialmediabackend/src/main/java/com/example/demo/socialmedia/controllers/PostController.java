package com.example.demo.socialmedia.controllers;


import com.example.demo.socialmedia.dtos.PostDTO;
import com.example.demo.socialmedia.entities.Post;
import com.example.demo.socialmedia.entities.User;
import com.example.demo.socialmedia.services.PostService;
import com.example.demo.socialmedia.services.UserService;
import com.example.demo.socialmedia.utils.ApiResponse;
import com.example.demo.socialmedia.utils.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    private PostService postService;

    private UserService userService;

    @Autowired
    public PostController(UserService userService,PostService postService) {
        this.postService = postService;
        this.userService = userService;
    }


    @GetMapping("posts")
    ResponseEntity<List<PostDTO>> getAllPosts() {
        List<Post> posts = postService.findAllPosts();
        List<PostDTO> postDTOs = posts.stream()
                .map(PostMapper::toPostDTO)
                .toList();
        return ResponseEntity.ok(postDTOs);
    }

    @PostMapping("{userid}/posts")
    ResponseEntity<ApiResponse<PostDTO>> savePost(@PathVariable String userid, @RequestBody Post post){
        User user = userService.findUserById(userid).get();
        post.setUser(user);
        Post tempPost = postService.savePost(post);
        PostDTO postDTO = PostMapper.toPostDTO(tempPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(postDTO,"Post Added Succesfylly"));
    }

    @GetMapping("posts/{postid}")
    ResponseEntity<ApiResponse<PostDTO>> getPostById(@PathVariable int postid){
        if(postService.findPostById(postid).isPresent()){
            Post post = postService.findPostById(postid).get();
            PostDTO postDTO = PostMapper.toPostDTO(post);
            return ResponseEntity.ok(new ApiResponse<>(postDTO,"Succces Response"));
        }
        else{
            return ResponseEntity.status(404).body(new ApiResponse<>(null,"Post not Found"));
        }
    }

    @GetMapping("{userid}/posts")
    ResponseEntity<ApiResponse<List<PostDTO>>> getPostByUser(@PathVariable String userid){
        List<Post> posts = postService.getPostsByUser(userid);
        List<PostDTO> postDTO = posts.stream().map(PostMapper::toPostDTO).toList();
        return ResponseEntity.ok(new ApiResponse<>(postDTO,"Success List"));
    }

    @DeleteMapping("posts/{postid}")
    public void deletePost(@PathVariable int postid){
        Post post = postService.findPostById(postid).get();
        postService.deletePost(post);
    }

}
