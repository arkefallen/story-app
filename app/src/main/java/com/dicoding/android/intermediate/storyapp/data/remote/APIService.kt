package com.dicoding.android.intermediate.storyapp.data.remote

import com.dicoding.android.intermediate.storyapp.data.response.LoginUserResponse
import com.dicoding.android.intermediate.storyapp.data.response.PostStoryResponse
import com.dicoding.android.intermediate.storyapp.data.response.RegisterUserResponse
import com.dicoding.android.intermediate.storyapp.data.response.UserStoriesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface APIService {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterUserResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginUserResponse>

    @GET("stories")
    suspend fun getStories(
        @Query("page") page : Int,
        @Query("size") size : Int,
        @Query("location") location : Int
    ) : UserStoriesResponse

    @Multipart
    @POST("stories")
    fun addStory(
        @Part("description") description : RequestBody,
        @Part file: MultipartBody.Part,
    ) : Call<PostStoryResponse>
}