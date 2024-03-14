package com.dev.githubuser.api

import com.dev.githubuser.data.model.DetailUserResponse
import com.dev.githubuser.data.model.User
import com.dev.githubuser.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query:String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}