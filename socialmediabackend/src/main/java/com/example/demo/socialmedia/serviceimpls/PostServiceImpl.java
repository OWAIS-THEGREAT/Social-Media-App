package com.example.demo.socialmedia.serviceimpls;

import com.example.demo.socialmedia.entities.Post;
import com.example.demo.socialmedia.entities.User;
import com.example.demo.socialmedia.repositories.PostRepository;
import com.example.demo.socialmedia.repositories.UserRepository;
import com.example.demo.socialmedia.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> findPostById(int id) {
        return postRepository.findById(id);
    }

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> getPostsByUser(String userid) {
        User user = userRepository.findById(userid).get();
        return user.getPosts();
    }

    @Override
    public void deletePost(Post post) {
        postRepository.delete(post);
    }

}
