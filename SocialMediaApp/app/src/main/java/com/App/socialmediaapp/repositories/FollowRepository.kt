package com.App.socialmediaapp.repositories

import com.App.socialmediaapp.remote.SocialApi
import com.App.socialmediaapp.remote.responsebody.Follow
import com.App.socialmediaapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FollowRepository @Inject constructor(
    private val api : SocialApi
) {

    fun followUser(userId: String, otherUserId: String) : Flow<Resource<Follow>> = flow {
        emit(Resource.Loading())

        try {
            val response = api.followUser(userId, otherUserId)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                    } ?: emit(Resource.Error("Response body is null"))
                } else {
                    emit(Resource.Error(response.message()))
                }
        } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    fun unfollowUser(userId: String, otherUserId: String) : Flow<Resource<Follow>> = flow {
        emit(Resource.Loading())

        try {
            val response = api.unfollowUser(userId, otherUserId)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                } ?: emit(Resource.Error("Response body is null"))
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown error occurred"))

        }
    }.flowOn(Dispatchers.IO)

    fun isFollowing(followerId: String, followeeId: String) : Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.isFollowing(followerId, followeeId)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                } ?: emit(Resource.Error("Response body is null"))
            } else {
                emit(Resource.Error(response.message()))
            }
        }
        catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }.flowOn(Dispatchers.IO)
}