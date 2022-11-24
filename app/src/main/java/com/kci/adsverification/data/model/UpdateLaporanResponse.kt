package com.kci.adsverification.data.model


import com.google.gson.annotations.SerializedName

data class UpdateLaporanResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)