package com.kci.adsverification.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kci.adsverification.data.Repository
import com.kci.adsverification.data.model.DataUserPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    private var _dataUser = MutableLiveData<DataUserPref>()
    val dataUser : LiveData<DataUserPref> get() = _dataUser
    fun deleteUserPref(){
        viewModelScope.launch {
            repository.deleteUserPref()
        }
    }
    fun logout(token:String){
        viewModelScope.launch {
            repository.logout(token)
        }
    }
    fun getUserPref(){
        viewModelScope.launch {
            repository.getUserPref().collect{
                _dataUser.postValue(it)
            }
        }
    }
}