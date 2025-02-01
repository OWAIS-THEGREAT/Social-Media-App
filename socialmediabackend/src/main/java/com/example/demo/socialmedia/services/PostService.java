package com.example.demo.socialmedia.services;

import com.example.demo.socialmedia.entities.Post;



import java.util.List;
import java.util.Optional;

public interface PostService {

    List<Post> findAllPosts();

    Optional<Post> findPostById(int id);

    Post savePost(Post post);

    List<Post> getPostsByUser(String userid);

    void deletePost(Post post);
}
