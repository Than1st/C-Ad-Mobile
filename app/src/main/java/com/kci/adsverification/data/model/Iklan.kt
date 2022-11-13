package com.kci.adsverification.data.model


import com.google.gson.annotations.SerializedName

data class Iklan(
    @SerializedName("created_at")
    val createdAt: Any,
    @SerializedName("durasi_akhir")
    val durasiAkhir: String,
    @SerializedName("durasi_awal")
    val durasiAwal: String,
    @SerializedName("id_iklan")
    val idIklan: Int,
    @SerializedName("id_trainset")
    val idTrainset: Int,
    @SerializedName("kategori_iklan")
    val kategoriIklan: String,
    @SerializedName("konten_iklan")
    val kontenIklan: String,
    @SerializedName("nama_agensi")
    val namaAgensi: String,
    @SerializedName("no_kontrak")
    val noKontrak: String,
    @SerializedName("updated_at")
    val updatedAt: Any,
    @SerializedName("nama_trainset")
    val namaTrainset: String
)