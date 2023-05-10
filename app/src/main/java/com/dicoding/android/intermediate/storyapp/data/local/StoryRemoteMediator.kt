package com.dicoding.android.intermediate.storyapp.data.local

import androidx.paging.ExperimentalPagingApi
import androidx.paging.*
import androidx.room.withTransaction
import com.dicoding.android.intermediate.storyapp.data.remote.APIService
import com.dicoding.android.intermediate.storyapp.data.response.Story

@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediator(
    private val database: StoryDatabase,
    private val apiService: APIService
) : RemoteMediator<Int, Story>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Story>): MediatorResult {
//        val page = when (loadType) {
//            LoadType.REFRESH -> {
//                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
//                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
//            }
//            LoadType.PREPEND -> {
//                val remoteKeys = getRemoteKeyForFirstItem(state)
//                val prevKey = remoteKeys?.prevKey
//                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
//                prevKey
//            }
//            LoadType.APPEND -> {
//                val remoteKeys = getRemoteKeyForLastItem(state)
//                val nextKey = remoteKeys?.nextKey
//                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
//                nextKey
//            }
//        }
        val page = INITIAL_PAGE_INDEX
        try {
            val userStoriesResponse = apiService.getStories(page, state.config.pageSize, 1)
            val stories = userStoriesResponse.listStory
            val endOfPaginationReached = stories?.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
//                    database.remoteKeysDao().deleteRemoteKeys()
                    database.storyDao().deleteAllStories()
                }
//                val prevKey = if (page == 1) null else page - 1
//                val nextKey = if (endOfPaginationReached!!) null else page + 1
//                val keys = stories.map {
//                    RemoteKeys(id = it.id.toString(), prevKey, nextKey)
//                }
//                database.remoteKeysDao().insertAll(keys)
                stories?.forEach {
                    database.storyDao().insertStory(it)
                }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached!!)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Story>) : RemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let {
            data -> database.remoteKeysDao().getRemoteKeysId(data.id.toString())
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Story>) : RemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let {
            data -> database.remoteKeysDao().getRemoteKeysId(data.id.toString())
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Story>) : RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeysDao().getRemoteKeysId(id)
            }
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}