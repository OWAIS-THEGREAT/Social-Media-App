package com.App.socialmediaapp.repositories

import com.App.socialmediaapp.remote.SocialApi
import com.App.socialmediaapp.remote.requestbody.CommentRequestBody
import com.App.socialmediaapp.remote.requestbody.PostRequestBody
import com.App.socialmediaapp.remote.responsebody.Post
import com.App.socialmediaapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val api: SocialApi
) {

    fun getPosts(): Flow<Resource<List<Post>>> = flow {
        emit(Resource.Loading())

        try {
            val response = api.getPosts()
            if (response.isSuccessful){
                response.body()?.let {
                    emit(Resource.Success(it))
                } ?: emit(Resource.Error("Response body is null"))
            }
            else{
                emit(Resource.Error("Failed to fetch posts"))
            }
        }
        catch (e : Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun likePost(userId: String, postId: Int) : Flow<Resource<Post>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.likePost(userId, postId)
            if (response.isSuccessful){
                response.body()?.let {
                    emit(Resource.Success(it))
                } ?: emit(Resource.Error("Response body is null"))
            }
            else{
                emit(Resource.Error("Failed to like post"))
            }
        }
        catch (e : Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun unlikePost(userId: String, postId: Int) : Flow<Resource<Post>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.unlikePost(userId, postId)
            if (response.isSuccessful){
                response.body()?.let {
                    emit(Resource.Success(it))
                } ?: emit(Resource.Error("Response body is null"))
            }
            else{
                emit(Resource.Error("Failed to like post"))
            }
        }
        catch (e : Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun uploadPost(userId : String,requestBody : PostRequestBody) : Flow<Resource<Post>> = flow {
        emit(Resource.Loading())

        try {
            val response = api.uploadPost(userId,requestBody)
            if(response.isSuccessful){
                response.body()?.let {
                    emit(Resource.Success(it))
                } ?: emit(Resource.Error("Response body is null"))
            }
            else{
                emit(Resource.Error("Failed to upload post"))
            }
        }
        catch (e : Exception){
            emit(Resource.Error(e.message.toString()))
        }

    }.flowOn(Dispatchers.IO)


    fun postComment(userId: String, postId: Int,comment : CommentRequestBody) : Flow<Resource<Post>> = flow {
        emit(Resource.Loading())

        try {
            val response = api.postComment(userId,postId,comment)

            if(response.isSuccessful){
                response.body()?.let {
                    emit(Resource.Success(it))
                } ?: emit(Resource.Error("Response body is null"))
            }
            else {
                emit(Resource.Error("Failed to post comment"))
            }
        }
        catch (e : Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

}