package com.dicoding.android.intermediate.storyapp.repo

import android.content.Context
import com.dicoding.android.intermediate.storyapp.data.local.StoryDatabase
import com.dicoding.android.intermediate.storyapp.data.remote.APIConfig

object Injection {
    fun provideAuth() : Authentication {
        val apiService = APIConfig.getAuthService()
        return Authentication.getInstance(apiService)
    }

    fun provideUserAuth(token: String, context: Context) : StoryRepository {
        val apiService = APIConfig.getAppService(token)
        val storyDatabase = StoryDatabase.getDatabase(context)
        return StoryRepository.getInstance(storyDatabase, apiService)
    }
}