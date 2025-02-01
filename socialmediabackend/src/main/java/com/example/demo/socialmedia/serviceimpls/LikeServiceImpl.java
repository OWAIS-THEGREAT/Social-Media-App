package com.example.demo.socialmedia.serviceimpls;


import com.example.demo.socialmedia.entities.Like;
import com.example.demo.socialmedia.entities.Post;
import com.example.demo.socialmedia.entities.User;
import com.example.demo.socialmedia.repositories.LikeRepository;
import com.example.demo.socialmedia.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

    private LikeRepository likeRepository;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public int getTotalLikes(int postid) {
        return likeRepository.getTotalLikes(postid);
    }

    @Override
    public Like likePost(User user, Post post) {
        return likeRepository.save(new Like(user,post));
    }

    @Override
    public void deleteLike(User user, Post post) {
        Like like = likeRepository.findByUserAndPost(user,post);
        likeRepository.delete(like);
    }
}
