package com.kci.adsverification.data.model


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("aktif")
    val aktif: String,
    @SerializedName("created_at")
    val createdAt: Any,
    @SerializedName("name")
    val name: String,
    @SerializedName("nik")
    val nik: Int,
    @SerializedName("updated_at")
    val updatedAt: Any,
    @SerializedName("username")
    val username: String
)