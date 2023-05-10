package com.dicoding.android.intermediate.storyapp.data.local

import androidx.paging.PagingSource
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.android.intermediate.storyapp.data.response.Story

interface StoryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: Story)

    @Query("SELECT * FROM story")
    fun getAllStories(): PagingSource<Int, Story>
}