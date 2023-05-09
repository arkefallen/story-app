package com.dicoding.android.intermediate.storyapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.android.intermediate.storyapp.data.response.PostStoryResponse
import com.dicoding.android.intermediate.storyapp.data.response.Story
import com.dicoding.android.intermediate.storyapp.repo.StoryRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun getAllStories() : LiveData<List<Story>?> = storyRepository.listStory

    fun getStories(
        page: Int,
        size: Int,
        location: Int
    ) {
        storyRepository.getStories(page, size, location)
    }

    fun addStory(
        image : MultipartBody.Part,
        description: RequestBody
    ) {
        viewModelScope.launch {
            storyRepository.addStory(image, description)
        }
    }

    fun getPostStoryReponses() : LiveData<PostStoryResponse> = storyRepository.uploadStoryResponse

    fun responseLoaded() : LiveData<Boolean> = storyRepository.isLoading
}