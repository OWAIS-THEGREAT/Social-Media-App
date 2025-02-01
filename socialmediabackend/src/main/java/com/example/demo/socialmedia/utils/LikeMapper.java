package com.example.demo.socialmedia.utils;

import com.example.demo.socialmedia.dtos.LikeDTO;
import com.example.demo.socialmedia.entities.Like;

public class LikeMapper {

    public static LikeDTO toLikeDTO(Like like) {
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setId(like.getId());
        likeDTO.setUserId(like.getUser().getId());
        likeDTO.setPostId(like.getPost().getId());
        return likeDTO;
    }

}
