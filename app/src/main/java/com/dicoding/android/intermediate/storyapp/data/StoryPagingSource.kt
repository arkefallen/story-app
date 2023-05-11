package com.dicoding.android.intermediate.storyapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.android.intermediate.storyapp.data.remote.APIService
import com.dicoding.android.intermediate.storyapp.data.response.Story

class StoryPagingSource(private val apiService: APIService)
    : PagingSource<Int, Story>() {
    override fun getRefreshKey(state: PagingState<Int, Story>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val userStoriesResponse = apiService.getPagingStories(page, params.loadSize, 1)
            val responseData = userStoriesResponse.listStory as List<Story>

            LoadResult.Page(
                data = responseData,
                prevKey = if (page == 1) null else page-1,
                nextKey = if (responseData.isNullOrEmpty()) null else page+1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}