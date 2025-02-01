package com.example.demo.socialmedia.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {


    @Id
    @Column(name = "User_Id")
    private String  id;
    @Column(name = "Username")
    private String username;
    @Column(name = "Password")
    private String password;
    @Column(name = "ProfilePic")
    private String profilepic;
    @Column(name = "Bio",length = 10000)
    private String bio;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference("user-post")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonManagedReference("user-like")
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonManagedReference("user-comment")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "followee", cascade = CascadeType.ALL)
    @JsonManagedReference("user-followee")
    private List<Follow> followers = new ArrayList<>();

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    @JsonManagedReference("user-follower")
    private List<Follow> following = new ArrayList<>();

    public User(String username, String password, String profilepic, String bio) {
        this.username = username;
        this.password = password;
        this.profilepic = profilepic;
        this.bio = bio;
    }
}
