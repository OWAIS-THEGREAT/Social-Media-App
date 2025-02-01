package com.example.demo.socialmedia.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private int id;
    private String content;
    private String userId;
    private int postId;
    private Date createdAt;
}