package com.App.socialmediaapp.remote.responsebody


data class UserPostResponse (
    var data : List<Post> = emptyList(),
    val message: String = ""
)