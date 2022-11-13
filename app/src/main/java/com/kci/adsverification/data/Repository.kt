package com.kci.adsverification.data

import com.kci.adsverification.data.api.ApiHelper
import com.kci.adsverification.data.datastore.UserPreferences
import com.kci.adsverification.data.model.LoginRequest

class Repository(private val apiHelper: ApiHelper, private val userPreferences: UserPreferences) {
    // API
    suspend fun login(loginRequest: LoginRequest) = apiHelper.login(loginRequest)
    suspend fun getIklan(access_token: String, id_iklan: Int) = apiHelper.getIklan(access_token, id_iklan)

    // Data Store
    suspend fun setUserPref(nik: String, nama: String, username: String, access_token:String) = userPreferences.setUserPref(nik, nama, username, access_token)
    fun getUserPref() = userPreferences.getUserPref()
    suspend fun deleteUserPref() = userPreferences.deleteUserPref()
}