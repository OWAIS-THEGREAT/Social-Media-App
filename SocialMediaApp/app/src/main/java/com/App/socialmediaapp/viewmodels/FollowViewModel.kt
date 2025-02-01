package com.App.socialmediaapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.App.socialmediaapp.remote.responsebody.Follow
import com.App.socialmediaapp.repositories.FollowRepository
import com.App.socialmediaapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowViewModel @Inject constructor(
    private val followRepository: FollowRepository
) : ViewModel(){

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    private val _follows = MutableStateFlow<Follow?>(null)
    val follows = _follows.asStateFlow()

    private val _isfollow = MutableStateFlow(false)
    val isfollow = _isfollow.asStateFlow()

    fun followUser(userId: String, otherUserId: String) {
        viewModelScope.launch {
            followRepository.followUser(userId,otherUserId).collectLatest {resource->
                when(resource){
                    is Resource.Loading -> {
                        _isLoading.value = true
                    }
                    is Resource.Success -> {
                        _isLoading.value = false
                        _isfollow.emit(true)
                        _follows.value = resource.data
                    }
                    is Resource.Error -> {
                        _isLoading.value = false
                        _error.value = resource.message
                    }
                }
            }
        }
    }

    fun unfollowUser(userId: String, otherUserId: String) {
        viewModelScope.launch {
            followRepository.unfollowUser(userId,otherUserId).collectLatest {resource->
                when(resource) {
                    is Resource.Loading -> {
                        _isLoading.value = true
                    }

                    is Resource.Success -> {
                        _isLoading.value = false
                        _isfollow.emit(false)
                        _follows.value = resource.data
                    }

                    is Resource.Error -> {
                        _isLoading.value = false
                        _error.value = resource.message
                    }
                }
            }
        }
    }

    fun isFollowing(followerId: String, followeeId: String) {
        viewModelScope.launch {
            followRepository.isFollowing(followerId, followeeId).collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _isLoading.value = true
                    }

                    is Resource.Success -> {
                        _isLoading.value = false
                        _isfollow.value = resource.data
                    }

                    is Resource.Error -> {
                        _isLoading.value = false
                        _error.value = resource.message
                    }
                }
            }
        }
    }
}