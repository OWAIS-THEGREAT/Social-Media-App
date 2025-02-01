package com.example.demo.socialmedia.utils;

import com.example.demo.socialmedia.dtos.PostDTO;
import com.example.demo.socialmedia.entities.Post;

import java.util.stream.Collectors;

public class PostMapper {

    public static PostDTO toPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setImageUrl(post.getImage_url());
        postDTO.setCaption(post.getCaption());
        postDTO.setCreatedAt(post.getCreatedAt());
        postDTO.setUserId(post.getUser().getId());

        // Convert Likes and Comments to DTOs
        postDTO.setLikes(post.getLikes().stream()
                .map(LikeMapper::toLikeDTO)
                .collect(Collectors.toList()));
        postDTO.setComments(post.getComments().stream()
                .map(CommentMapper::toCommentDTO)
                .collect(Collectors.toList()));

        return postDTO;
    }

}
