package com.atsalaja.githubuserapp.data.retrofit

import com.atsalaja.githubuserapp.data.response.DetailUserResponse
import com.atsalaja.githubuserapp.data.response.GithubResponse
import com.atsalaja.githubuserapp.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun searchUsers(
        @Query("q") q: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowersUser(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowingUser(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}