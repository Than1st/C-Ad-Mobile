package com.kci.adsverification.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kci.adsverification.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: Repository): ViewModel() {
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
}