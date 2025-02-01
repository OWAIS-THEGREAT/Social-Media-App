package com.App.socialmediaapp.remote.responsebody

data class Comment(
    val content: String,
    val createdAt: String,
    val id: Int,
    val postId: Int,
    val userId: String
)