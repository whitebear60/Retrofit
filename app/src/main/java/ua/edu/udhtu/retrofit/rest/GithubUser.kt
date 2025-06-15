package ua.edu.udhtu.retrofit.rest

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUser (
    @SerializedName("login") val username: String,
//    @SerializedName("id") val id: Int,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("repos_url") val reposUrl: String,
    @SerializedName("name") val name: String?,
    @SerializedName("company") val company: String?,
    @SerializedName("location") val location: String?,
//    @SerializedName("email") val email: String?,
    @SerializedName("public_repos") val repoCount: Int?,
    @SerializedName("followers") val followers: Int?,
    @SerializedName("blog") val blogUrl: String?
) : Parcelable