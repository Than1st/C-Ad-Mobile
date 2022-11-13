package com.kci.adsverification.data.api

import com.kci.adsverification.data.model.IklanResponse
import com.kci.adsverification.data.model.LoginRequest
import com.kci.adsverification.data.model.LoginResponse
import retrofit2.http.*

interface ApiService {
    @POST("api/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
    @GET("api/iklan/{id}")
    suspend fun getIklan(
        @Header("Authorization") token : String,
        @Path("id") id_iklan:Int
    ): IklanResponse
}