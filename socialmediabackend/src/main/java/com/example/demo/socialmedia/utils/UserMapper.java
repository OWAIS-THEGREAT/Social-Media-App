package com.example.demo.socialmedia.utils;

import com.example.demo.socialmedia.dtos.UserDTO;
import com.example.demo.socialmedia.entities.Comment;
import com.example.demo.socialmedia.entities.Like;
import com.example.demo.socialmedia.entities.Post;
import com.example.demo.socialmedia.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getProfilepic(),
                user.getBio(),
                user.getPosts().stream()
                        .map(Post::getId) // Extracting post IDs
                        .collect(Collectors.toList()),
                user.getLikes().stream()
                        .map(Like::getId) // Extracting like IDs
                        .collect(Collectors.toList()),
                user.getComments().stream()
                        .map(Comment::getId) // Extracting comment IDs
                        .collect(Collectors.toList()),
                user.getFollowers().stream()
                        .map(follow -> follow.getFollower().getId()) // Extracting follower IDs
                        .collect(Collectors.toList()),
                user.getFollowing().stream()
                        .map(follow -> follow.getFollowee().getId()) // Extracting following IDs
                        .collect(Collectors.toList())
        );
    }
}
