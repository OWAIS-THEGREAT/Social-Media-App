package com.example.demo.socialmedia.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Follow")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", nullable = false)
    @JsonBackReference("user-follower")
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followee_id", nullable = false)
    @JsonBackReference("user-followee")
    private User followee;

    public Follow(User follower, User followee) {
        this.follower = follower;
        this.followee = followee;
    }
}
