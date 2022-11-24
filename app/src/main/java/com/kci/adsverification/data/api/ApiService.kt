package com.kci.adsverification.data.api

import com.kci.adsverification.data.model.*
import retrofit2.http.*

interface ApiService {
    @POST("api/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @GET("api/iklan/{id}")
    suspend fun getIklan(
        @Header("Authorization") token: String,
        @Path("id") id_iklan: Int
    ): IklanResponse

    @POST("api/update/{id}")
    suspend fun updateLaporan(
        @Header("Authorization") token: String,
        @Path("id") id_iklan: Int,
        @Body updateLaporanRequest: UpdateLaporanRequest
    ): UpdateLaporanResponse

    @POST("api/logout")
    suspend fun logout(
        @Header("Authorization") token: String
    )
}