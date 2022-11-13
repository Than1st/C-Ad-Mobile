package com.kci.adsverification.data.model


import com.google.gson.annotations.SerializedName

data class DataUserPref(
    @SerializedName("nik")
    val nik: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("access_token")
    val accessToken: String
)