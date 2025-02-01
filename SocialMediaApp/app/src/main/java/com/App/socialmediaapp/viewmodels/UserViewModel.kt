package com.App.socialmediaapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.App.socialmediaapp.remote.requestbody.UserRequestBody
import com.App.socialmediaapp.remote.responsebody.UserResponse
import com.App.socialmediaapp.repositories.UserRepository
import com.App.socialmediaapp.utils.Resource
import com.App.socialmediaapp.utils.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences
) : ViewModel(){

    private val userCache = mutableMapOf<String, UserResponse>()

    private val _userId = MutableStateFlow<String?>(null)
    val userId = _userId.asStateFlow()

    init {
        fetchUserId()
    }

    private fun fetchUserId() {
        viewModelScope.launch {
            userPreferences.userId.collect { id ->
                _userId.value = id
            }
        }
    }


    private val _isLoading = MutableStateFlow(false)
    val isloading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    private val _loginUser = MutableStateFlow(UserResponse())
    val loginUser = _loginUser.asStateFlow()

    private val _getUserById = MutableStateFlow(UserResponse())
    val getUserById = _getUserById.asStateFlow()

    private val _saveUser = MutableStateFlow(UserResponse())
    val saveUser = _saveUser.asStateFlow()



    fun clearError(){
        _error.value = ""
    }

    fun clearLoginUser() {
        _loginUser.value = UserResponse()
    }

    fun loginUser(userBody : UserRequestBody){
        viewModelScope.launch {
            userRepository.loginUser(userBody).collectLatest {resource->
                when(resource){
                    is Resource.Error -> {
                        _error.emit(resource.message)
                        _isLoading.emit(false)
                    }
                    is Resource.Loading -> {
                        _isLoading.emit(true)
                    }
                    is Resource.Success -> {
                        _loginUser.emit(resource.data)
                        _isLoading.emit(false)
                    }
                }
            }
        }
    }

    fun saveUser(userBody : UserRequestBody){
        viewModelScope.launch {
            userRepository.saveUser(userBody).collectLatest {resource->
                when(resource) {
                    is Resource.Error -> {
                        resource.message.let {
                            _error.emit(it)
                        }
                        _isLoading.emit(false)
                    }
                    is Resource.Loading -> {
                        _isLoading.emit(true)
                    }
                    is Resource.Success -> {
                        resource.data.let {
                            _saveUser.emit(it)
                            _isLoading.emit(false)
                        }
                    }
                }
            }
        }
    }

    fun getUserById(userId  : String){

        val cachedUser = userCache[userId]
        if (cachedUser != null) {

            if (_getUserById.value != cachedUser) {
                _getUserById.value = cachedUser
            }

        }
        else {
            viewModelScope.launch {
                userRepository.getUserById(userId).collectLatest { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            _error.emit(resource.message)
                            _isLoading.emit(false)
                        }

                        is Resource.Loading -> {
                            _isLoading.emit(true)
                        }

                        is Resource.Success -> {
                            userCache[userId] = resource.data
                            if (_getUserById.value != resource.data) {
                                _getUserById.emit(resource.data)
                            }
                            _isLoading.emit(false)
                        }
                    }
                }
            }
        }
    }
}