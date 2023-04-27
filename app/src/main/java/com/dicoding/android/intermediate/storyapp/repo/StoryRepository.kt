package com.dicoding.android.intermediate.storyapp.repo


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.android.intermediate.storyapp.data.remote.APIService
import com.dicoding.android.intermediate.storyapp.data.response.Story
import com.dicoding.android.intermediate.storyapp.data.response.UserStoriesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StoryRepository private constructor(
    private val apiService: APIService
){
    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoadingLiveData

    private val _storyResponseLiveData = MutableLiveData<UserStoriesResponse>()
    val storiesResponse : LiveData<UserStoriesResponse> = _storyResponseLiveData

    private val _listStoryLiveData = MutableLiveData<List<Story>?>()
    val listStory : LiveData<List<Story>?> = _listStoryLiveData

    fun getStories(
        page: Int,
        size: Int,
        location: Int
    ) {
        _isLoadingLiveData.value = false

        val client = apiService.getStories(page,size,location)

        client.enqueue(
            object : Callback<UserStoriesResponse> {
                override fun onResponse(
                    call: Call<UserStoriesResponse>,
                    response: Response<UserStoriesResponse>
                ) {
                    _isLoadingLiveData.value = true
                    val responseBody = response.body()
                    Log.e(TAG, "onResponse: ${response.message()}")
                    val storiesResponse = UserStoriesResponse(
                        responseBody?.listStory,
                        responseBody?.error,
                        responseBody?.message
                    )
                    _storyResponseLiveData.value = storiesResponse
                    _listStoryLiveData.value = responseBody?.listStory
                }

                override fun onFailure(call: Call<UserStoriesResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                    val storiesResponse = UserStoriesResponse(
                        ArrayList(),
                        true,
                        t.message
                    )
                    _storyResponseLiveData.value = storiesResponse
                    _listStoryLiveData.value = ArrayList()
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