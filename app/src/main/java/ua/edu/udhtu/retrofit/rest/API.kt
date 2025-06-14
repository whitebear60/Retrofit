package ua.edu.udhtu.retrofit.rest

import retrofit2.Call
import retrofit2.http.GET

interface API {
    @GET("fact")
    fun getRandomFact(): Call<ResponseMain>
}