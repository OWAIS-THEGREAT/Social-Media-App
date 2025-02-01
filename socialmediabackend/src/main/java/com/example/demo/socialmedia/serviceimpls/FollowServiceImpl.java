package com.example.demo.socialmedia.serviceimpls;

import com.example.demo.socialmedia.entities.Follow;
import com.example.demo.socialmedia.entities.User;
import com.example.demo.socialmedia.repositories.FollowRepository;
import com.example.demo.socialmedia.services.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl implements FollowService {

    private FollowRepository followRepository;

    @Autowired
    public FollowServiceImpl(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    @Override
    public Follow follow(User follower, User followee) {
        Follow follow = followRepository.save(new Follow(follower,followee));
        return follow;
    }

    @Override
    public Follow unfollow(User follower, User followee) {
        Follow follow = followRepository.findByFollowerAndFollowee(follower,followee);
        followRepository.delete(follow);
        return follow;
    }

    @Override
    public boolean isFollowing(User follower, User followee) {
        return followRepository.existsByFollowerAndFollowee(follower, followee);
    }
}
