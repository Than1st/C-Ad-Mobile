package com.kci.adsverification.ui.dataiklan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kci.adsverification.data.Repository
import com.kci.adsverification.data.Resource
import com.kci.adsverification.data.model.DataUserPref
import com.kci.adsverification.data.model.IklanResponse
import com.kci.adsverification.data.model.LoginRequest
import com.kci.adsverification.data.model.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataIklanViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    private var _userPreferences = MutableLiveData<DataUserPref>()
    val userPreferences : LiveData<DataUserPref> get() = _userPreferences

    private val _iklan = MutableLiveData<Resource<IklanResponse>>()
    val iklan: LiveData<Resource<IklanResponse>> get() = _iklan

    fun getIklan(access_token: String, id_iklan: Int) {
        viewModelScope.launch {
            _iklan.postValue(Resource.loading())
            try {
                _iklan.postValue(Resource.success(repository.getIklan(access_token, id_iklan)))
            } catch (e: Exception) {
                _iklan.postValue(Resource.error(e.message ?: "Error"))
            }
        }
    }

    fun getUserPref(){
        viewModelScope.launch {
            repository.getUserPref().collect{
                _userPreferences.postValue(it)
            }
        }
    }
}