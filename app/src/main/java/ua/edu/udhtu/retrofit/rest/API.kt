package ua.edu.udhtu.retrofit.rest

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("latest/tick")
    fun getExchangeRate(
        @Query("market") market: String,
        @Query("instruments") instruments: String
    ): Call<ResponseMain>
}