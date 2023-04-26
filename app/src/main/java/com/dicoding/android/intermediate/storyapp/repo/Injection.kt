package com.dicoding.android.intermediate.storyapp.repo

import android.content.Context
import com.dicoding.android.intermediate.storyapp.data.remote.APIConfig
import com.dicoding.android.intermediate.storyapp.repo.Authentication

object Injection {
    fun provideAuth(context: Context) : Authentication {
        val apiService = APIConfig.getService()
        return Authentication.getInstance(apiService)
    }
}