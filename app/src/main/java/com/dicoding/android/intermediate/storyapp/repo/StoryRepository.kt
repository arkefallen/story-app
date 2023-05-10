package com.dicoding.android.intermediate.storyapp.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.android.intermediate.storyapp.data.StoryPagingSource
import com.dicoding.android.intermediate.storyapp.data.remote.APIService
import com.dicoding.android.intermediate.storyapp.data.response.PostStoryResponse
import com.dicoding.android.intermediate.storyapp.data.response.Story
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StoryRepository private constructor(
    private val apiService: APIService
){
    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoadingLiveData

    private val _uploadStoryLiveData = MutableLiveData<PostStoryResponse>()
    val uploadStoryResponse : LiveData<PostStoryResponse> = _uploadStoryLiveData

    private val _listStoryLiveData = MutableLiveData<List<Story>?>()
    val listStory : LiveData<List<Story>?> = _listStoryLiveData

    fun getStories() : LiveData<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = 4
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
            }
        ).liveData
    }

//    fun getStories(
//        page: Int,
//        size: Int,
//        location: Int
//    ) {
//        _isLoadingLiveData.value = false
//
//        val client = apiService.getStories(page,size,location)
//
//        client.enqueue(
//            object : Callback<UserStoriesResponse> {
//                override fun onResponse(
//                    call: Call<UserStoriesResponse>,
//                    response: Response<UserStoriesResponse>
//                ) {
//                    _isLoadingLiveData.value = true
//                    val responseBody = response.body()
//                    Log.e(TAG, "onResponse: ${response.message()}")
//                    _listStoryLiveData.value = responseBody?.listStory
//                }
//
//                override fun onFailure(call: Call<UserStoriesResponse>, t: Throwable) {
//                    Log.e(TAG, "onFailure: ${t.message}")
//                    _listStoryLiveData.value = ArrayList()
//                }
//            }
//        )
//    }

    fun addStory(
        image : MultipartBody.Part,
        description: RequestBody
    ) {
        _isLoadingLiveData.value = true

        val client = apiService.addStory(description, image)

        client.enqueue(
            object : Callback<PostStoryResponse> {
                override fun onResponse(
                    call: Call<PostStoryResponse>,
                    response: Response<PostStoryResponse>
                ) {
                    _isLoadingLiveData.value = true
                    val responseBody = response.body()
                    Log.e(TAG, "onResponse: ${response.message()}")
                    if (responseBody != null) {
                        val uploadStoryResponse = PostStoryResponse(
                            responseBody.error,
                            responseBody.message
                        )
                        _uploadStoryLiveData.value = uploadStoryResponse
                    }
                }

                override fun onFailure(call: Call<PostStoryResponse>, t: Throwable) {
                    Log.e(TAG, "onResponse: ${t.message}")
                    val uploadStoryResponse = PostStoryResponse(
                        true,
                        t.message.toString()
                    )
                    _uploadStoryLiveData.value = uploadStoryResponse
                }
            }
        )
    }

    companion object {
        private val TAG = this::class.java.simpleName
        private var instance: StoryRepository? = null

        fun getInstance(
            apiService: APIService,
        ) : StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService)
            }.also { instance = it }
    }
}