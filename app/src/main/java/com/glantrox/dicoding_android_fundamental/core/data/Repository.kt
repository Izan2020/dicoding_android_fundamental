package com.glantrox.dicoding_android_fundamental.core.data

import com.glantrox.dicoding_android_fundamental.core.models.DetailResponse
import com.glantrox.dicoding_android_fundamental.core.models.SearchResponse
import com.glantrox.dicoding_android_fundamental.core.models.UsersResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Repository  {
    companion object {
        const val API_KEY = "token {Token Github Anda}"

        fun create(): Repository {
            return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Repository::class.java)
        }
    }

    @GET("users")
    suspend fun fetchListOfUsers(
        @Header("Authorization") key: String = API_KEY
    ): UsersResponse

    @GET("users/{username}")
    suspend fun fetchUserDetailByUsername(
        @Path("username") username: String,
        @Header("Authorization") key: String = API_KEY
    ): DetailResponse

    @GET("users/{username}/followers")
    suspend fun fetchUsersFollowersByUsername(
        @Path("username") username: String,
        @Header("Authorization") key: String = API_KEY
    ): UsersResponse

    @GET("users/{username}/following")
    suspend fun fetchUsersFollowingByUsername(
        @Path("username") username: String,
        @Header("Authorization") key: String = API_KEY
    ): UsersResponse

    @GET("search/users")
    suspend fun searchUserByUsername(
        @Query("q") username: String,
        @Header("Authorization") key: String = API_KEY
    ): SearchResponse

}