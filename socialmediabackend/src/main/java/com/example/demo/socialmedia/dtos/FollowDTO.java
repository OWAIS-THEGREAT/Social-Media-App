package com.example.demo.socialmedia.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FollowDTO {
    private int id;
    private String followerId;
    private String followeeId;
}

