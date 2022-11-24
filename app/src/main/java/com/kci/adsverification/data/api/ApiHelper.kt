package com.kci.adsverification.data.api

import com.kci.adsverification.data.model.LoginRequest
import com.kci.adsverification.data.model.UpdateLaporanRequest

class ApiHelper(private val apiService: ApiService) {
    suspend fun login(loginRequest: LoginRequest) = apiService.login(loginRequest)
    suspend fun getIklan(access_token: String, id_iklan: Int) = apiService.getIklan(access_token, id_iklan)
    suspend fun updateLaporan(token: String, id_iklan: Int, updateLaporanRequest: UpdateLaporanRequest) = apiService.updateLaporan(token, id_iklan, updateLaporanRequest)
    suspend fun logout(token: String) = apiService.logout(token)
}