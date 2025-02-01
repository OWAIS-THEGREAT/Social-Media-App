package com.example.demo.socialmedia.repositories;

import com.example.demo.socialmedia.entities.Like;
import com.example.demo.socialmedia.entities.Post;
import com.example.demo.socialmedia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like,Integer> {

    @Query("SELECT COUNT(l) FROM Like l WHERE l.post.id = :postId")
    int getTotalLikes(@Param("postId") int postId);

    Like findByUserAndPost(User user, Post post);
}
