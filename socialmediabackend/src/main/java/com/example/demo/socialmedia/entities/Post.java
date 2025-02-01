package com.example.demo.socialmedia.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Post")
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "imageUrl")
    private String image_url;

    @Column(name = "Caption")
    private String caption;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreatedAt")
    private Date createdAt = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    @JsonBackReference("user-post")
    private User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    @JsonManagedReference("post-like")
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    @JsonManagedReference("post-comment")
    private List<Comment> comments = new ArrayList<>();

    public Post(String image_url, String caption, Date createdAt,User user) {
        this.image_url = image_url;
        this.caption = caption;
        this.createdAt = createdAt;
        this.user = user;
    }


}
