package com.example.demo.socialmedia.services;

import com.example.demo.socialmedia.entities.Follow;
import com.example.demo.socialmedia.entities.User;

public interface FollowService {

    Follow follow(User follower, User followee);

    Follow unfollow(User follower, User followee);

    public boolean isFollowing(User follower, User followee);

}
