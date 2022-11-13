package com.kci.adsverification.ui.scanqr

import androidx.lifecycle.ViewModel
import com.kci.adsverification.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScanQrViewModel @Inject constructor(private val repository: Repository): ViewModel() {
}