package com.App.socialmediaapp.remote.responsebody

data class UserResponse(
    var data: UserData? = null,
    val message: String = ""
)