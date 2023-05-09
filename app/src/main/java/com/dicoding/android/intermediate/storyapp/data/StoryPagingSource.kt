package com.dicoding.android.intermediate.storyapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.android.intermediate.storyapp.data.remote.APIService
import com.dicoding.android.intermediate.storyapp.data.response.Story

class StoryPagingSource(private val apiService: APIService)
    : PagingSource<Int, Story>() {
    override fun getRefreshKey(state: PagingState<Int, Story>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        TODO("Not yet implemented")
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}