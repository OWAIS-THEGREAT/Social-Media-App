package com.example.demo.socialmedia.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {

    private int id;
    private String imageUrl;
    private String caption;
    private Date createdAt;
    private String userId;
    private List<LikeDTO> likes;
    private List<CommentDTO> comments;

}

