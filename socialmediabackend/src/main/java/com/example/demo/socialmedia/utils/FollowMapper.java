package com.example.demo.socialmedia.utils;

import com.example.demo.socialmedia.dtos.FollowDTO;
import com.example.demo.socialmedia.entities.Follow;

public class FollowMapper {

    public static FollowDTO toFollowDTO(Follow follow) {
        FollowDTO followDTO = new FollowDTO();
        followDTO.setId(follow.getId());
        followDTO.setFollowerId(follow.getFollower().getId());
        followDTO.setFolloweeId(follow.getFollowee().getId());
        return followDTO;
    }

}
