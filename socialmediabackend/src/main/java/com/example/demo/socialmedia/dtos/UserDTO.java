package com.example.demo.socialmedia.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String id;
    private String username;
    private String profilePic;
    private String bio;
    private List<Integer> postIds;
    private List<Integer> likeIds;
    private List<Integer> commentIds;
    private List<String> followerIds;
    private List<String> followingIds;
}
