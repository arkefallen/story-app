package com.dicoding.android.intermediate.storyapp.data.remote

import com.dicoding.android.intermediate.storyapp.data.response.LoginUserResponse
import com.dicoding.android.intermediate.storyapp.data.response.RegisterUserResponse
import com.dicoding.android.intermediate.storyapp.data.response.UserStoriesResponse
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
    fun getStories(
        @Query("page") page : Int,
        @Query("size") size : Int,
        @Query("location") location : Int
    ) : Call<UserStoriesResponse>
}