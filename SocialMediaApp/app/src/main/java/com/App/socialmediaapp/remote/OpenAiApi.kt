package com.App.socialmediaapp.remote

import com.App.socialmediaapp.remote.requestbody.ChatBotRequestBody
import com.App.socialmediaapp.remote.responsebody.ChatBotResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAiApi {

    @POST("blenderbot-400M-distill")
    suspend fun getChatResponse(
        @Body request: ChatBotRequestBody
    ): ChatBotResponse

}