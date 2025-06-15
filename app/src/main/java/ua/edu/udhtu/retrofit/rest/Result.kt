package ua.edu.udhtu.retrofit.rest

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val isIncomplete: Boolean,
    @SerializedName("items") val items: ArrayList<GithubUser>
)

