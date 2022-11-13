package com.kci.adsverification.data.api

import com.kci.adsverification.data.model.LoginRequest

class ApiHelper(private val apiService: ApiService) {
    suspend fun login(loginRequest: LoginRequest) = apiService.login(loginRequest)
    suspend fun getIklan(access_token: String, id_iklan: Int) = apiService.getIklan(access_token, id_iklan)
}