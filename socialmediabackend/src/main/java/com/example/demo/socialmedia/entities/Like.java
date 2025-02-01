package com.example.demo.socialmedia.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Likes")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("user-like")
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("post-like")
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;

    public Like(User user,Post post) {
        this.post = post;
        this.user = user;
    }
}
