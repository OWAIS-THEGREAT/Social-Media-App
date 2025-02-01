package com.App.socialmediaapp.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.App.socialmediaapp.remote.requestbody.CommentRequestBody
import com.App.socialmediaapp.remote.responsebody.Comment
import com.App.socialmediaapp.viewmodels.PostViewModel
import com.App.socialmediaapp.viewmodels.UserViewModel
import com.App.socialmediaapp.views.CommentBottomSheet
import com.App.socialmediaapp.views.mainpages.SocialMediaMainPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialMediaMainScreen(userViewModel: UserViewModel,postViewModel: PostViewModel, navController: NavHostController){

    val loading = postViewModel.isLoading.collectAsState(initial = false)
    val error = postViewModel.error.collectAsState(initial = "")
    val posts = postViewModel.posts.collectAsState(initial = emptyList())

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isSheetVisible by remember { mutableStateOf(false) }
    var selectedComments by remember { mutableStateOf(emptyList<Comment>()) }

    var commentingUser by remember { mutableStateOf("") }
    var commentingpostid by remember { mutableStateOf(0) }

    selectedComments = posts.value.flatMap { post ->
        if (post.id == commentingpostid) {
            post.comments
        } else {
            emptyList()
        }

    }
//    var i by remember { mutableIntStateOf(0) }
    Log.d("comments",selectedComments.size.toString())
//    i += 1
//    Log.d("data",posts.value.toString())
    LaunchedEffect(Unit) {
        Log.d("SocialMediaMainScreen", "LaunchedEffect triggered")
        postViewModel.getPosts()
    }

    if(loading.value){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    else if(error.value.isNotEmpty()){
        Log.e("Error", error.value)
    }
    else{
        SocialMediaMainPage(
            posts.value, postViewModel,userViewModel,
            onLikeClicked = {},
            onProfileClicked = {profileid->
                navController.navigate("OtherProfilePageScreen/$profileid")
            },
            onOwnProfileClicked = {currentuser->
                navController.navigate("ProfilePageScreen/$currentuser")
            },
            onAddPostClicked = {currentuser->
                navController.navigate("AddPostScreen/$currentuser")
            },
            onCommentClicked = {comments,userid,postid->
                selectedComments = comments
                isSheetVisible = true
                commentingUser = userid
                commentingpostid = postid
            },
            onChatBotClicked = {
                navController.navigate("ChatBotScreen")
            }
        )
    }

    CommentBottomSheet(
        isSheetVisible = isSheetVisible,
        sheetState = sheetState,
        comments = selectedComments,
        userViewModel = userViewModel,
        onClose = {
            isSheetVisible = false
        },
        onPostComment = {comment->
            postViewModel.postComment(commentingUser,commentingpostid, CommentRequestBody(content = comment))
            Log.d("hello",commentingUser)
        }
    )
}