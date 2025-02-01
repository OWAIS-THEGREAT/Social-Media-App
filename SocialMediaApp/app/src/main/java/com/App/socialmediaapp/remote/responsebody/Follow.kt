package com.App.socialmediaapp.remote.responsebody

data class Follow(
    val id : Int,
    val followerId: String,
    val followingId: String
)
