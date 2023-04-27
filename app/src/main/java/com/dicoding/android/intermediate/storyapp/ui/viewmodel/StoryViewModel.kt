package com.dicoding.android.intermediate.storyapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.android.intermediate.storyapp.data.response.Story
import com.dicoding.android.intermediate.storyapp.repo.StoryRepository
import com.dicoding.android.intermediate.storyapp.repo.UserPreferences

class StoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun getAllStories() : LiveData<List<Story>?> = storyRepository.listStory

    fun getStories(
        page: Int,
        size: Int,
        location: Int
    ) {
        storyRepository.getStories(page, size, location)
    }

}