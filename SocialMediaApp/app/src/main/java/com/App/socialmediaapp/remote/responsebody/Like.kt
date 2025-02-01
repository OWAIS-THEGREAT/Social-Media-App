package com.App.socialmediaapp.remote.responsebody

data class Like(
    val id: Int = -1,
    val postId: Int,
    val userId: String
)