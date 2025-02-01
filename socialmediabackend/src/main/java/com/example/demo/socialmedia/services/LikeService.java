package com.example.demo.socialmedia.services;

import com.example.demo.socialmedia.entities.Like;
import com.example.demo.socialmedia.entities.Post;
import com.example.demo.socialmedia.entities.User;
import javafx.geometry.Pos;

public interface LikeService {

    int getTotalLikes(int postid);

    Like likePost(User user, Post post);

    void deleteLike(User user, Post post);
}
