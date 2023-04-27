package com.dicoding.android.intermediate.storyapp.repo

import android.content.Context
import com.dicoding.android.intermediate.storyapp.data.remote.APIConfig

object Injection {
    fun provideAuth() : Authentication {
        val apiService = APIConfig.getAuthService()
        return Authentication.getInstance(apiService)
    }

    fun provideUserAuth(token: String) : StoryRepository {
        val apiService = APIConfig.getAppService(token)
        return StoryRepository.getInstance(apiService)
    }
}