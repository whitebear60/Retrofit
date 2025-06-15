package ua.edu.udhtu.retrofit.rest

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubRepository(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("language") val language: String?,
    @SerializedName("stargazers_count") val stars: Int,
    @SerializedName("forks_count") val forks: Int,
    @SerializedName("license") val license: License?,
    @SerializedName("fork") val isFork: Boolean
) : Parcelable