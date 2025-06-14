package ua.edu.udhtu.retrofit.rest

import com.google.gson.annotations.SerializedName

data class ResponseMain(
    @SerializedName("fact") val fact: String,
    @SerializedName("length") val length: Int
)
