package com.kci.adsverification.data.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("iklan")
    val iklan: List<Iklan>,
    @SerializedName("kategori")
    val kategori: List<Kategori>,
    @SerializedName("kereta_iklan")
    val keretaIklan: List<KeretaIklan>,
    @SerializedName("titik")
    val titik: List<Titik>,
    @SerializedName("trainset_iklan")
    val trainsetIklan: List<TrainsetIklan>
)