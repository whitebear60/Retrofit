package ua.edu.udhtu.retrofit.rest

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 30
    ): Call<Result>

    @GET("users/{username}")
    fun getUser(
        @Path("username") user: String
    ): Call<GithubUser>

    @GET("users/{username}/repos")
    fun getUserRepos(
        @Path("username") user: String,
        @Query("per_page") perPage: Int = 30
    ): Call<ArrayList<GithubRepository>>
}