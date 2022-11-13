package com.kci.adsverification.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kci.adsverification.data.Repository
import com.kci.adsverification.data.Resource
import com.kci.adsverification.data.model.DataUserPref
import com.kci.adsverification.data.model.LoginRequest
import com.kci.adsverification.data.model.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private var _alreadyLogin = MutableLiveData<DataUserPref>()
    val alreadyLogin : LiveData<DataUserPref> get() = _alreadyLogin

    private val _login = MutableLiveData<Resource<LoginResponse>>()
    val login: LiveData<Resource<LoginResponse>> get() = _login

    fun authLogin(loginRequest: LoginRequest) {
        viewModelScope.launch {
            _login.postValue(Resource.loading())
            try {
                _login.postValue(Resource.success(repository.login(loginRequest)))
            } catch (e: Exception) {
                _login.postValue(Resource.error(e.message ?: "Error"))
            }
        }
    }

    fun getUserPref(){
        viewModelScope.launch {
            repository.getUserPref().collect{
                _alreadyLogin.postValue(it)
            }
        }
    }

    fun setUserPref(nik: String, nama: String, username: String, access_token:String) {
        viewModelScope.launch {
            repository.setUserPref(nik, nama, username, access_token)
        }
    }
}