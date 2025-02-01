package com.App.socialmediaapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.App.socialmediaapp.repositories.ChatBotRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatBotViewModel @Inject constructor(
    private val chatBotRepository: ChatBotRepository
) : ViewModel() {

    private val _response = mutableStateOf<List<String>>(emptyList())
    val response: State<List<String>> get() = _response

    fun sendMessage(userMessage: String) {
        viewModelScope.launch {
            _response.value = try {
                chatBotRepository.getChatResponse(userMessage)
            } catch (e: Exception) {
                listOf("Error: ${e.message}")
            }
        }
    }
}