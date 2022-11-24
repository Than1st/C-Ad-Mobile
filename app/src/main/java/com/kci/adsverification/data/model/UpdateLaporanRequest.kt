package com.kci.adsverification.data.model


import com.google.gson.annotations.SerializedName

data class UpdateLaporanRequest(
    @SerializedName("catatan")
    val catatan: String,
    @SerializedName("hasil")
    val hasil: String,
    @SerializedName("nik")
    val nik: String,
    @SerializedName("status")
    val status: Int
)