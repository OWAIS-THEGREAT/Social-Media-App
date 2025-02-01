package com.App.socialmediaapp.repositories

import android.util.Log
import com.App.socialmediaapp.remote.SocialApi
import com.App.socialmediaapp.remote.requestbody.UserRequestBody
import com.App.socialmediaapp.remote.responsebody.UserResponse
import com.App.socialmediaapp.utils.Resource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: SocialApi
) {

    fun loginUser(userBody : UserRequestBody) : Flow<Resource<UserResponse>> = flow {
        emit(Resource.Loading())

        try {
            val response = api.login(userBody)
            if(response.isSuccessful && response.body() != null){
                emit(Resource.Success(response.body()!!))
            }
            else{
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                val errorResponse = try {
                    Gson().fromJson(errorBody, UserResponse::class.java)
                } catch (e: Exception) {
                    UserResponse(message = errorBody)
                }

                emit(Resource.Error(errorResponse.message))
            }
        }catch (e : Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun saveUser(userBody : UserRequestBody) : Flow<Resource<UserResponse>> = flow {
        emit(Resource.Loading())

        try {
            val response = api.saveUser(userBody)
            if(response.isSuccessful && response.body() != null){
                emit(Resource.Success(response.body()!!))
            }
            else{
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                val errorResponse = try {
                    Gson().fromJson(errorBody, UserResponse::class.java)
                } catch (e: Exception) {
                    UserResponse(message = errorBody)
                }

                emit(Resource.Error(errorResponse.message))
            }
        }catch (e : Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun getUserById(userId  : String) : Flow<Resource<UserResponse>> = flow {
        emit(Resource.Loading())

        try {
            val response = api.getUserById(userId)
            if(response.isSuccessful && response.body() != null){
                emit(Resource.Success(response.body()!!))
            }
            else{
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                val errorResponse = try {
                    Gson().fromJson(errorBody, UserResponse::class.java)
                } catch (e: Exception) {
                    UserResponse(message = errorBody)
                }

                emit(Resource.Error(errorResponse.message))
            }
        } catch (e : Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }


}