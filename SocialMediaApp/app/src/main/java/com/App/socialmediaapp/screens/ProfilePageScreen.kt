package com.App.socialmediaapp.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.App.socialmediaapp.viewmodels.ProfileViewModel
import com.App.socialmediaapp.viewmodels.UserViewModel
import com.App.socialmediaapp.views.profilepage.ProfilePage

@Composable
fun ProfilePageScreen(
    userid: String,
    userViewModel: UserViewModel,
    profileViewModel: ProfileViewModel
){

    val user = userViewModel.getUserById.collectAsState(initial = null)
    val isloading = userViewModel.isloading.collectAsState(initial = false)
    val error = userViewModel.error.collectAsState(initial = "")
    val userposts = profileViewModel.userposts.collectAsState(initial = null)


    LaunchedEffect(Unit) {
        profileViewModel.getUserPosts(userid)
    }

    LaunchedEffect(Unit) {
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
        ProfilePage(user.value!!.data!!,userposts.value!!.data)
    }

}

