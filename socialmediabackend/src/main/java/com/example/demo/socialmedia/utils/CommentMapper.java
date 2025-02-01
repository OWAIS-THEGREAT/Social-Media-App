package com.example.demo.socialmedia.utils;

import com.example.demo.socialmedia.dtos.CommentDTO;
import com.example.demo.socialmedia.entities.Comment;

public class CommentMapper {

    public static CommentDTO toCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setUserId(comment.getUser().getId()); // Fetching User ID
        commentDTO.setPostId(comment.getPost().getId()); // Fetching Post ID
        commentDTO.setCreatedAt(comment.getCreatedAt());
        return commentDTO;
    }
}
