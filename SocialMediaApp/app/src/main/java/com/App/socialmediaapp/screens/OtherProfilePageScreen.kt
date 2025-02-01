package com.App.socialmediaapp.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.App.socialmediaapp.viewmodels.FollowViewModel
import com.App.socialmediaapp.viewmodels.ProfileViewModel
import com.App.socialmediaapp.viewmodels.UserViewModel
import com.App.socialmediaapp.views.profilepage.OtherProfilePage

@Composable
fun OtherProfilePageScreen(
    userid: String,
    userViewModel: UserViewModel,
    profileViewModel: ProfileViewModel,
    followViewModel: FollowViewModel
){
    val currentUserId by userViewModel.userId.collectAsState()

    val user = userViewModel.getUserById.collectAsState(initial = null)
    val isloading = userViewModel.isloading.collectAsState(initial = false)
    val error = userViewModel.error.collectAsState(initial = "")
    val userposts = profileViewModel.userposts.collectAsState(initial = null)
    val follow = followViewModel.follows.collectAsState(initial = null)
    val isfollow = followViewModel.isfollow.collectAsState(initial = false)

    LaunchedEffect(Unit) {
        profileViewModel.getUserPosts(userid)
    }

    LaunchedEffect(Unit) {
        userViewModel.getUserById(userid)
    }

    LaunchedEffect(Unit) {
        Log.d("userid",userid)
        Log.d("currentuserid",currentUserId!!)
        followViewModel.isFollowing(currentUserId!!,userid)
    }

    LaunchedEffect(isfollow.value) {
        userViewModel.getUserById(userid)
    }

    if(isloading.value){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }
    }
    else if(error.value.isNotEmpty()) {
        Log.e("Error", error.value)
    }
    else if(user.value != null && userposts.value != null){
        Log.d("true/false",isfollow.value.toString())
            OtherProfilePage(
                currentUserId!!,user.value!!.data!!,userposts.value!!.data,isfollow.value
                ,onFollowClicked = {
                    followViewModel.followUser(currentUserId!!,userid)
                },
                onUnFollowClicked = {
                    followViewModel.unfollowUser(currentUserId!!,userid)
                }
            )
    }
}