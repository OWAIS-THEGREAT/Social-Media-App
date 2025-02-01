package com.App.socialmediaapp.di

import android.content.Context
import com.App.socialmediaapp.remote.OpenAiApi
import com.App.socialmediaapp.remote.SocialApi
import com.App.socialmediaapp.repositories.ChatBotRepository
import com.App.socialmediaapp.repositories.FollowRepository
import com.App.socialmediaapp.repositories.PostRepository
import com.App.socialmediaapp.repositories.ProfileRepository
import com.App.socialmediaapp.repositories.UserRepository
import com.App.socialmediaapp.utils.Constants
import com.App.socialmediaapp.utils.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSocialApi(retrofit: Retrofit): SocialApi {
        return retrofit.create(SocialApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(socialApi: SocialApi): UserRepository {
        return UserRepository(socialApi)
    }

    @Provides
    @Singleton
    fun providePostRepository(socialApi: SocialApi): PostRepository {
        return PostRepository(socialApi)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(socialApi: SocialApi): ProfileRepository {
        return ProfileRepository(socialApi)
    }

    @Provides
    @Singleton
    fun provodeFollowRepository(socialApi: SocialApi): FollowRepository {
        return FollowRepository(socialApi)
    }

    @Provides
    @Singleton
    fun provideUserPreferences(
        @ApplicationContext context: Context
    ): UserPreferences {
        return UserPreferences(context)
    }

    @Provides
    @Singleton
    @Named("OpenAiClient")
    fun provideOpenAiOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${Constants.CHATBOT_KEY}")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Named("OpenAiRetrofit")
    fun provideOpenAiRetrofit(
        @Named("OpenAiClient") client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.CHATBOTBASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenAiApi(@Named("OpenAiRetrofit") retrofit: Retrofit): OpenAiApi {
        return retrofit.create(OpenAiApi::class.java)
    }

    @Provides
    @Singleton
    fun provideChatBotRepository(api: OpenAiApi): ChatBotRepository {
        return ChatBotRepository(api)
    }
}