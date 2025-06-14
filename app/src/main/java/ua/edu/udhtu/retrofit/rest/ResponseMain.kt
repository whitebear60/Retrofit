package ua.edu.udhtu.retrofit.rest

import com.google.gson.annotations.SerializedName

data class ResponseMain(
    @SerializedName("Data") val data: CoinbaseResponseData,
    @SerializedName("length") val length: Int
)

data class CoinbaseResponseData(
    @SerializedName("BTC-USD") val rates: Rates
)

data class Rates (
    @SerializedName("VALUE") val value: Double
)
