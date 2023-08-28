package com.glantrox.dicoding_android_fundamental.core.data.remote

import com.glantrox.dicoding_android_fundamental.core.models.DetailResponse
import com.glantrox.dicoding_android_fundamental.core.models.SearchResponse
import com.glantrox.dicoding_android_fundamental.core.models.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface  {

    @GET("users")
    suspend fun fetchListOfUsers(): UsersResponse

    @GET("users/{username}")
    suspend fun fetchUserDetailByUsername(
        @Path("username") username: String,
    ): DetailResponse

    @GET("users/{username}/followers")
    suspend fun fetchUsersFollowersByUsername(
        @Path("username") username: String,
    ): UsersResponse

    @GET("users/{username}/following")
    suspend fun fetchUsersFollowingByUsername(
        @Path("username") username: String,
    ): UsersResponse

    @GET("search/users")
    suspend fun searchUserByUsername(
        @Query("q") username: String,
    ): SearchResponse

}