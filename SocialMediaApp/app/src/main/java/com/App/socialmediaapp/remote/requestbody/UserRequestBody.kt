package com.App.socialmediaapp.remote.requestbody

data class UserRequestBody(
    val bio: String = "",
    val password: String,
    val profilepic: String = "",
    val username: String
)