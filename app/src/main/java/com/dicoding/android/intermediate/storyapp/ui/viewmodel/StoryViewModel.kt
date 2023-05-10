package com.dicoding.android.intermediate.storyapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.android.intermediate.storyapp.data.response.PostStoryResponse
import com.dicoding.android.intermediate.storyapp.data.response.Story
import com.dicoding.android.intermediate.storyapp.repo.StoryRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun getStories() : LiveData<PagingData<Story>> = storyRepository.getStories().cachedIn(viewModelScope)

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