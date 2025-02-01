package com.App.socialmediaapp.remote.responsebody

data class UserData(
    val bio: String,
    val commentIds: List<Int>,
    val followerIds: List<String>,
    val followingIds: List<String>,
    val id: String,
    val likeIds: List<Int>,
    val postIds: List<Int>,
    val profilePic: String,
    val username: String
)