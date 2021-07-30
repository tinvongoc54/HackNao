package com.vtd.hacknao.ui.splash

import androidx.lifecycle.viewModelScope
import com.vtd.hacknao.common.base.BaseViewModel
import com.vtd.hacknao.repository.DatabaseRepository
import com.vtd.hacknao.utils.liveData.SingleLiveData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActViewModel(private val dbRepository: DatabaseRepository) : BaseViewModel() {
    var delaySplash = SingleLiveData<Boolean>()

    fun delay() {
        viewModelScope.launch {
            dbRepository.getTargetList()
            delay(1500)
            delaySplash.value = true
        }
    }
}