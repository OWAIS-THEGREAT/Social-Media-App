package com.App.socialmediaapp.views.mainpages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.ModeComment
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.App.socialmediaapp.R
import com.App.socialmediaapp.remote.responsebody.Comment
import com.App.socialmediaapp.remote.responsebody.Like
import com.App.socialmediaapp.remote.responsebody.Post
import com.App.socialmediaapp.remote.responsebody.UserData
import com.App.socialmediaapp.ui.theme.MainThemeColor
import com.App.socialmediaapp.viewmodels.PostViewModel
import com.App.socialmediaapp.viewmodels.UserViewModel

@Composable
fun SocialMediaMainPage(posts : List<Post>, postViewModel: PostViewModel,userViewModel: UserViewModel,onLikeClicked: () -> Unit,onProfileClicked: (String) -> Unit,onOwnProfileClicked: (String) -> Unit,onAddPostClicked: (String?) -> Unit,onCommentClicked: (List<Comment>,String,Int) -> Unit,onChatBotClicked: () -> Unit) {
    val currentUserId by userViewModel.userId.collectAsState()
    val userCache = remember { mutableMapOf<String, UserData?>() }

    LaunchedEffect(posts) {
        posts.forEach { post ->

            if (userCache[post.userId] == null) {
                Log.d("hello",post.userId)
                userViewModel.getUserById(post.userId)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
        ) {

            TopBar(){
                onChatBotClicked()
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 5.dp)
            ) {
                items(posts) { thisPost ->
                    val userId = thisPost.userId

                    val user = getUserDetail(userId,userViewModel)

                    Log.d("data", user.toString())

                    if (user != null) {
                        PostItem(
                            postid = thisPost.id,
                            profileImageUrl = user.profilePic,
                            userid = userId,
                            username = user.username,
                            currentuserid = currentUserId!!,
                            postImageurl = thisPost.imageUrl,
                            Likes = thisPost.likes,
                            Comments = thisPost.comments,
                            postViewModel = postViewModel,
                            listOfComments = thisPost.comments,
                            onLikeClicked = {
                                onLikeClicked()
                            },
                            onProfileClicked = {profileid->
                                onProfileClicked(profileid)
                            },
                            onOwnProfileClicked = {currentuser->
                                onOwnProfileClicked(currentuser)
                            },
                            onCommentClicked = {comments,userid,postid->
                                onCommentClicked(comments,userid,postid)
                            }
                        )
                    } else {

                        Text(text = "Loading user data...", color = Color.Gray)
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = {
                onAddPostClicked(currentUserId)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(56.dp),
            containerColor = MainThemeColor
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Post",
                tint = Color.White
            )
        }
    }
}

@Composable
fun TopBar(onChatBotClicked:()->Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.AccountBox,
                contentDescription = "Settings",
                modifier = Modifier.size(24.dp),
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Social Sphere",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Chat,
            contentDescription = "Chat",
            modifier = Modifier.size(30.dp)
                .clickable {
                    onChatBotClicked()
                },
            tint = Color.Black
        )
    }
}

@Composable
fun PostItem(postid : Int,profileImageUrl: String,userid : String, username: String,currentuserid : String, postImageurl: String, Likes:List<Like>, Comments: List<Comment>,postViewModel: PostViewModel,listOfComments: List<Comment>,onLikeClicked: ()->(Unit),onProfileClicked: (String) ->(Unit),onOwnProfileClicked: (String) -> Unit,onCommentClicked: (List<Comment>,String,Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MainThemeColor)
            .padding(bottom = 10.dp, top = 10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 10.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(profileImageUrl.ifEmpty { null })
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.owais),
                error = painterResource(R.drawable.owais),
                contentDescription = "Post Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .clickable {
                        if(userid==currentuserid){
                            onOwnProfileClicked(currentuserid)

                        }
                        else{
                            onProfileClicked(userid)
                        }

                    }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = username, color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable {
                    if(userid==currentuserid){
                        onOwnProfileClicked(currentuserid)

                    }
                    else{
                        onProfileClicked(userid)
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(postImageurl.ifEmpty { null })
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.owais),
            error = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "Post Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )

        Spacer(modifier = Modifier.height(10.dp))


        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 10.dp)
        ) {
            Icon(
                imageVector = if (isPostLikedByUser(currentuserid, Likes)) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = "Like",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        if(isPostLikedByUser(currentuserid,Likes)){
                            postViewModel.unlikePost(currentuserid,postid){
                                onLikeClicked()
                            }
                        }
                        else{
                            postViewModel.likePost(currentuserid,postid){
                                onLikeClicked()
                            }
                        }
                    },
                tint = if (isPostLikedByUser(currentuserid, Likes)) Color.Red else Color.Black
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = Likes.size.toString(),
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )


            Spacer(modifier = Modifier.width(16.dp))


            Icon(
                imageVector = Icons.Outlined.ModeComment,
                contentDescription = "Comment",
                modifier = Modifier.size(32.dp)
                    .clickable {
                        onCommentClicked(listOfComments,currentuserid,postid)
                    },
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = Comments.size.toString(),
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


@Composable
fun getUserDetail(userId: String, userViewModel: UserViewModel): UserData? {
    val user = userViewModel.getUserById.collectAsState(initial = null)
    LaunchedEffect(userId) {
            userViewModel.getUserById(userId)
    }
    return user.value?.data
}

fun isPostLikedByUser(currentUserId: String, likes: List<Like>): Boolean {
    return likes.any { it.userId == currentUserId }
}
