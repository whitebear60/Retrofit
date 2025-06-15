package ua.edu.udhtu.retrofit.rest

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class License (
    @SerializedName("name") val name: String,
    @SerializedName("key") val key: String
): Parcelable
