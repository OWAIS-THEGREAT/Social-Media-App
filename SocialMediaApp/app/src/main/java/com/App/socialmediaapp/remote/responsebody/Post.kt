package com.App.socialmediaapp.remote.responsebody

data class Post(
    val caption: String,
    val comments: List<Comment>,
    val createdAt: String,
    val id: Int,
    val imageUrl: String,
    val likes: List<Like>,
    val userId: String
)