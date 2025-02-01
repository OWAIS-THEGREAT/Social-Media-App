package com.App.socialmediaapp.repositories

import com.App.socialmediaapp.remote.OpenAiApi
import com.App.socialmediaapp.remote.requestbody.ChatBotRequestBody
import javax.inject.Inject

class ChatBotRepository @Inject constructor(
    private val api: OpenAiApi
) {

    suspend fun getChatResponse(userMessage: String): List<String> {
        val request = ChatBotRequestBody(userMessage)
        return try {
            val response = api.getChatResponse(request)

            response?.map { it.generated_text } ?: listOf("No response from server")

        } catch (e: Exception) {
            listOf("Error: ${e.message}")
        }
    }

}