package com.example.demo.socialmedia.controllers;

import com.example.demo.socialmedia.dtos.FollowDTO;
import com.example.demo.socialmedia.entities.Follow;
import com.example.demo.socialmedia.entities.User;
import com.example.demo.socialmedia.services.FollowService;
import com.example.demo.socialmedia.services.UserService;
import com.example.demo.socialmedia.utils.FollowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FollowController {

    private UserService userService;

    private FollowService followService;

    @Autowired
    public FollowController(UserService userService, FollowService followService) {
        this.userService = userService;
        this.followService = followService;
    }

    @PostMapping("{followerid}/follow/{followeeid}")
    public FollowDTO followUser(@PathVariable String followerid, @PathVariable String followeeid){
        User follower = userService.findUserById(followerid).get();
        User followee = userService.findUserById(followeeid).get();
        return FollowMapper.toFollowDTO(followService.follow(follower,followee));
    }

    @DeleteMapping("{followerid}/follow/{followeeid}")
    public FollowDTO unfollowUser(@PathVariable String followerid,@PathVariable String followeeid){

        User follower = userService.findUserById(followerid).get();
        User followee = userService.findUserById(followeeid).get();
        return FollowMapper.toFollowDTO(followService.unfollow(follower,followee));
    }

    @GetMapping("{followerid}/follows/{followeeid}")
    public boolean checkFollow(@PathVariable String followerid, @PathVariable String followeeid) {
        User follower = userService.findUserById(followerid).orElseThrow(() -> new RuntimeException("Follower not found"));
        User followee = userService.findUserById(followeeid).orElseThrow(() -> new RuntimeException("Followee not found"));
        return followService.isFollowing(follower, followee);
    }
}
