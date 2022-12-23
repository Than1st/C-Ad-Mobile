package com.kci.adsverification.data.model


import com.google.gson.annotations.SerializedName

data class IklanResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String
)