package com.example.demo.socialmedia.utils;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ApiResponse<T> {
    private T data;
    private String message;

}

