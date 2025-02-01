package com.App.socialmediaapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.App.socialmediaapp.remote.responsebody.UserPostResponse
import com.App.socialmediaapp.repositories.ProfileRepository
import com.App.socialmediaapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel(){

    private val _isloading = MutableStateFlow(false)
    val isloading = _isloading.asStateFlow()

    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    private val _userposts = MutableStateFlow(UserPostResponse())
    val userposts = _userposts.asStateFlow()

    fun getUserPosts(userid : String){
        viewModelScope.launch {
            profileRepository.getUserPosts(userid).collectLatest {resource->
                when(resource){
                    is Resource.Error -> {
                        _error.emit(resource.message)
                        _isloading.emit(false)
                    }
                    is Resource.Loading -> {
                        _isloading.emit(true)
                    }
                    is Resource.Success -> {
                        _userposts.emit(resource.data)
                        _isloading.emit(false)
                    }
                }
            }
        }
    }

}