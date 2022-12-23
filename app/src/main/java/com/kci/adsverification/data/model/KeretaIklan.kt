package com.kci.adsverification.data.model


import com.google.gson.annotations.SerializedName

data class KeretaIklan(
    @SerializedName("nama_kereta")
    val namaKereta: String
)