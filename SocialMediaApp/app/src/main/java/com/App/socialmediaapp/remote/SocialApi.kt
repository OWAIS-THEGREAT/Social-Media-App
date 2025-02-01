package com.App.socialmediaapp.remote

import com.App.socialmediaapp.remote.requestbody.CommentRequestBody
import com.App.socialmediaapp.remote.requestbody.PostRequestBody
import com.App.socialmediaapp.remote.requestbody.UserRequestBody
import com.App.socialmediaapp.remote.responsebody.Follow
import com.App.socialmediaapp.remote.responsebody.Like
import com.App.socialmediaapp.remote.responsebody.Post
import com.App.socialmediaapp.remote.responsebody.UserPostResponse
import com.App.socialmediaapp.remote.responsebody.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SocialApi {

    @POST("login")
    suspend fun login(
        @Body user : UserRequestBody
    ): Response<UserResponse>

    @POST("user")
    suspend fun saveUser(
        @Body user : UserRequestBody
    ) : Response<UserResponse>

    @GET("posts")
    suspend fun getPosts() : Response<List<Post>>

    @GET("user/{userId}")
    suspend fun getUserById(
        @Path("userId") userId: String
    ):Response<UserResponse>

    @POST("{userid}/like/{postid}")
    suspend fun likePost(
        @Path("userid") userId: String,
        @Path("postid") postId: Int
    ): Response<Post>

    @DELETE("{userid}/like/{postid}")
    suspend fun unlikePost(
        @Path("userid") userid : String,
        @Path("postid") postid : Int
    ):Response<Post>

    @GET("{userid}/posts")
    suspend fun getUserPosts(
        @Path("userid") userId: String
    ):Response<UserPostResponse>

    @POST("{userid}/follow/{otheruserid}")
    suspend fun followUser(
        @Path("userid") userId: String,
        @Path("otheruserid") otherUserId: String
    ):Response<Follow>

    @DELETE("{userid}/follow/{otheruserid}")
    suspend fun unfollowUser(
        @Path("userid") userId: String,
        @Path("otheruserid") otherUserId: String
    ):Response<Follow>

    @GET("{userid}/follows/{otheruserid}")
    suspend fun isFollowing(
        @Path("userid") followerId: String,
        @Path("otheruserid") followeeId: String
    ):Response<Boolean>

    @POST("{userid}/posts")
    suspend fun uploadPost(
        @Path("userid") userId: String,
        @Body post: PostRequestBody
    ):Response<Post>

    @POST("{userid}/comment/{postid}")
    suspend fun postComment(
        @Path("userid") userId: String,
        @Path("postid") postId: Int,
        @Body comment : CommentRequestBody
    ):Response<Post>
}