package com.App.socialmediaapp.repositories

import com.App.socialmediaapp.remote.SocialApi
import com.App.socialmediaapp.remote.responsebody.UserPostResponse
import com.App.socialmediaapp.remote.responsebody.UserResponse
import com.App.socialmediaapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val profileApi: SocialApi
) {

    fun getUserPosts(userid : String) : Flow<Resource<UserPostResponse>> = flow {
        emit(Resource.Loading())

        try {
            val response = profileApi.getUserPosts(userid)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                } ?: emit(Resource.Error("Response body is null"))
                } else {
                emit(Resource.Error("Failed to fetch user posts"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred"))
        }
    }.flowOn(Dispatchers.IO)

}