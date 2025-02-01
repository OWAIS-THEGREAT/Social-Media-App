package com.App.socialmediaapp.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.App.socialmediaapp.remote.requestbody.CommentRequestBody
import com.App.socialmediaapp.remote.requestbody.PostRequestBody
import com.App.socialmediaapp.remote.responsebody.Post
import com.App.socialmediaapp.repositories.PostRepository
import com.App.socialmediaapp.utils.Resource
import com.App.socialmediaapp.views.authentication.Login
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    private val _islikeLoading = MutableStateFlow(false)
    val islikeLoading = _islikeLoading.asStateFlow()

    private val _likeerror = MutableStateFlow("")
    val likeerror = _likeerror.asStateFlow()

    private val _isunlikeLoading = MutableStateFlow(false)
    val isunlikeLoading = _isunlikeLoading.asStateFlow()

    private val _unlikeerror = MutableStateFlow("")
    val unlikeerror = _unlikeerror.asStateFlow()

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts = _posts.asStateFlow()

    private val _uploadresponse = MutableStateFlow<Post?>(null)
    val uploadresponse = _uploadresponse.asStateFlow()

    private val _uploaderror = MutableStateFlow("")
    val uploaderror = _uploaderror.asStateFlow()

    private val _uploadisLoading = MutableStateFlow(false)
    val uploadisLoading = _uploadisLoading.asStateFlow()

    private val _commentresponse = MutableStateFlow<Post?>(null)
    val commentresponse = _commentresponse.asStateFlow()

    private val _commerror = MutableStateFlow("")
    val commerror = _commerror.asStateFlow()

    private val _commentisLoading = MutableStateFlow(false)
    val commentisLoading = _commentisLoading.asStateFlow()

    var hasFetchtedPosts = false

    val selectedImageFileState = mutableStateOf<File?>(null)
    val selectedImageTimestamp = mutableStateOf(0L)

    fun getPosts(){

        viewModelScope.launch {
            postRepository.getPosts().collectLatest {resource->
                when(resource){
                    is Resource.Error -> {
                        resource.message.let {
                            _error.emit(it)
                            _isLoading.emit(false)
                        }
                    }
                    is Resource.Loading -> {
                        _isLoading.emit(true)
                    }
                    is Resource.Success -> {
                        resource.data.let {posts->
                            _posts.emit(posts)
                            _isLoading.emit(false)
                        }
                    }
                }
            }
        }
    }

    fun likePost(userId: String, postId: Int,onSuccess: ()->Unit){
        viewModelScope.launch {
            postRepository.likePost(userId, postId).collectLatest { resource ->
                when (resource) {
                    is Resource.Error -> {
                        resource.message.let {
                            _likeerror.emit(it)
                            _islikeLoading.emit(false)
                        }
                    }
                    is Resource.Loading -> {
                        _islikeLoading.emit(true)
                    }
                    is Resource.Success -> {
                        val updatedPosts = _posts.value.map { post ->
                            if (post.id == postId) {
                                resource.data
                            } else {
                                post
                            }
                        }
                        _posts.emit(updatedPosts)
                        onSuccess()
                        _islikeLoading.emit(false)
                    }
                }
            }
        }
    }

    fun unlikePost(userId: String, postId: Int,onSuccess: ()->Unit){
        viewModelScope.launch {
            postRepository.unlikePost(userId, postId).collectLatest { resource ->
                when (resource) {
                    is Resource.Error -> {
                        resource.message.let {
                            _unlikeerror.emit(it)
                            _isunlikeLoading.emit(false)
                        }
                    }
                    is Resource.Loading -> {
                        _isunlikeLoading.emit(true)
                    }
                    is Resource.Success -> {
                        val updatedPosts = _posts.value.map { post ->
                            if (post.id == postId) {
                                resource.data
                            } else {
                                post
                            }
                        }
                        _posts.emit(updatedPosts)
                        onSuccess()
                        _isunlikeLoading.emit(false)
                    }
                }
            }
        }
    }

    fun uploadPost(userId: String,requestBody : PostRequestBody){

        viewModelScope.launch {
            postRepository.uploadPost(userId,requestBody).collectLatest {resource->

                when(resource){
                    is Resource.Error -> {
                        resource.message.let {
                            _uploaderror.emit(it)
                            _uploadisLoading.emit(false)
                        }
                    }
                    is Resource.Loading -> {
                        _uploadisLoading.emit(true)
                    }
                    is Resource.Success ->{
                        resource.data.let {
                            _uploadresponse.emit(it)
                            _uploadisLoading.emit(false)
                        }
                    }
                }
            }
        }
    }

    fun postComment(userId: String, postId: Int,comment : CommentRequestBody){

        viewModelScope.launch {
            postRepository.postComment(userId,postId,comment).collectLatest {resource->

                when(resource){
                    is Resource.Error -> {
                        resource.message.let {
                            _commerror.emit(it)
                            _commentisLoading.emit(false)
                        }
                    }
                    is Resource.Loading -> {
                        _commentisLoading.emit(true)
                    }
                    is Resource.Success -> {
                        val updatedPosts = _posts.value.map { post ->
                            if (post.id == postId) {
                                Log.d("updated data",post.toString())
                                resource.data
                            } else {
                                post
                            }
                        }

                        Log.d("updated data",updatedPosts.toString())
                        _posts.emit(updatedPosts)
                        _commentisLoading.emit(false)
                    }
                }
            }
        }

    }

}