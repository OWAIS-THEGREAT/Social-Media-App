package com.App.socialmediaapp.views.profilepage

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.App.socialmediaapp.R
import com.App.socialmediaapp.remote.responsebody.Post
import com.App.socialmediaapp.remote.responsebody.UserData
import com.App.socialmediaapp.ui.theme.MainBackground
import com.App.socialmediaapp.viewmodels.FollowViewModel

@Composable
fun OtherProfilePage(currentuser : String,user: UserData, posts: List<Post>, isfollow: Boolean,onFollowClicked: () -> Unit,onUnFollowClicked: () -> Unit){

    Log.d("posting",posts.toString())
    Column(
        modifier = Modifier.padding(top = 20.dp).fillMaxWidth().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OtherTopBar()
        Spacer(Modifier.height(30.dp))
        OtherProfileImageSection(user.profilePic,user.username)
        Spacer(Modifier.height(30.dp))

        Card(modifier = Modifier.padding(horizontal = 20.dp)
            .shadow(5.dp, shape = RoundedCornerShape(10.dp))
            ,colors = CardDefaults.cardColors(containerColor = Color.LightGray)) {
            Spacer(Modifier.height(20.dp))
            OtherPostFollowerSection(user.postIds.size,user.followerIds.size,user.followingIds.size)
            Spacer(Modifier.height(20.dp))
        }
        Spacer(Modifier.height(30.dp))
        Button(
            onClick = {
                if(isfollow){
                    onUnFollowClicked()
                }else{
                    onFollowClicked()
                }
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            colors = if(!isfollow)ButtonDefaults.buttonColors(MainBackground)else ButtonDefaults.buttonColors(Color.Gray),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = if(!isfollow)"Follow" else "Unfollow")
        }
        Spacer(Modifier.height(30.dp))
        OtherAboutSecction(user.bio)
        Spacer(Modifier.height(30.dp))
        OtherPostSection(posts)
    }
}

@Composable
@Preview
fun OtherTopBar(){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
        )
        Text(text = "Profile", color = Color.Black, fontSize = 20.sp)
        TextButton(
            onClick = {},
        ) {
            Text(text = "Edit", color = Color.Black, fontSize = 17.sp)
        }
    }
}

@Composable
fun OtherProfileImageSection(profileImage : String,username : String){

    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 20.dp)
    ){
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(profileImage.ifEmpty { null })
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.owais),
            error = painterResource(R.drawable.owais),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .weight(0.3f)
                .size(150.dp)
                .shadow(10.dp, shape = RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp)
                )
        )
        Spacer(modifier = Modifier.width(10.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(0.5f)
        ) {
            Text(text = username, color = Color.Black, fontSize = 20.sp)
        }
    }
}

@Composable
fun OtherPostFollowerSection(noOfPosts : Int,noOfFollowers : Int,noOfFollowing : Int){
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = noOfPosts.toString(), color = Color.Black, fontSize = 22.sp)
            Text(text = "Posts", color = Color.Gray, fontSize = 17.sp)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = noOfFollowers.toString(), color = Color.Black, fontSize = 22.sp)
            Text(text = "Followers", color = Color.Gray, fontSize = 17.sp)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = noOfFollowing.toString(), color = Color.Black, fontSize = 22.sp)
            Text(text = "Following", color = Color.Gray, fontSize = 17.sp)
        }
    }
}

@Composable
fun OtherAboutSecction(bio: String) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
    ) {
        Text(text = "About", color = Color.Black, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = bio
            , color = Color.Gray, fontSize = 15.sp
        )
    }
}

@Composable
fun OtherPostSection(posts: List<Post>) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
    ) {
        Text(text = "Post", color = Color.Black, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(10.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(0.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(posts) { post ->
                OtherPostImage(imageUrl = post.imageUrl, contentDescription = post.caption)
            }
        }
    }
}

@Composable
fun OtherPostImage(imageUrl: String, contentDescription: String) {

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl.ifEmpty { null })
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.owais),
        error = painterResource(R.drawable.owais),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.LightGray)
    )
}