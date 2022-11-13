package com.kci.adsverification.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kci.adsverification.data.model.DataUserPref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(private val context: Context) {
    companion object{
        private const val USER_PREF = "USER_PREF"
        private val TOKEN_KEY = stringPreferencesKey("TOKEN_KEY")
        private val NIK_VERIFIKATOR_KEY = stringPreferencesKey("NIK_KEY")
        private val NAMA_VERIFIKATOR_KEY = stringPreferencesKey("NAMA_KEY")
        private val USERNAME_VERIFIKATOR_KEY = stringPreferencesKey("USERNAME_KEY")
        private val ACCESS_TOKEN_VERIFIKATOR_KEY = stringPreferencesKey("ACCESS_TOKEN_KEY")
        const val DEFAULT_TOKEN = "default_token"
        const val DEF_NIK = "0"
        const val DEF_NAMA = "def_nama"
        const val DEF_USERNAME = "def_username"
        const val DEF_ACCESS_TOKEN = "def_access_token"
        val Context.datastore by preferencesDataStore(USER_PREF)
    }
//    suspend fun setToken(token: String){
//        context.datastore.edit { preferences ->
//            preferences[TOKEN_KEY] = token
//        }
//    }

    suspend fun setUserPref(nik:String, nama:String, username:String, access_token:String){
        context.datastore.edit { pref ->
            pref[NIK_VERIFIKATOR_KEY] = nik
            pref[NAMA_VERIFIKATOR_KEY] = nama
            pref[USERNAME_VERIFIKATOR_KEY] = username
            pref[ACCESS_TOKEN_VERIFIKATOR_KEY] = access_token
        }
    }

    fun getUserPref() : Flow<DataUserPref> {
        return context.datastore.data.map { pref ->
            DataUserPref(
                pref[NIK_VERIFIKATOR_KEY] ?: DEF_NIK,
                pref[NAMA_VERIFIKATOR_KEY] ?: DEF_NAMA,
                pref[USERNAME_VERIFIKATOR_KEY] ?: DEF_USERNAME,
                pref[ACCESS_TOKEN_VERIFIKATOR_KEY] ?: DEF_ACCESS_TOKEN
            )
        }
    }

    suspend fun deleteUserPref() {
        context.datastore.edit { preferences ->
            preferences.clear()
//            preferences[NIK_VERIFIKATOR_KEY] = DEF_NIK
//            preferences[NAMA_VERIFIKATOR_KEY] = DEF_NAMA
//            preferences[USERNAME_VERIFIKATOR_KEY] = DEF_USERNAME
        }
    }
}