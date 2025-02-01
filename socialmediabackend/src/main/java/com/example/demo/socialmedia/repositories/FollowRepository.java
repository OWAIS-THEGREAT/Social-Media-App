package com.example.demo.socialmedia.repositories;

import com.example.demo.socialmedia.entities.Follow;
import com.example.demo.socialmedia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow,Integer> {

    Follow findByFollowerAndFollowee(User follower,User followee);

    boolean existsByFollowerAndFollowee(User follower, User followee);
}
