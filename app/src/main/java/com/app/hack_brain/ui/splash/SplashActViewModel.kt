package com.app.hack_brain.ui.splash

import androidx.lifecycle.viewModelScope
import com.app.hack_brain.common.base.BaseViewModel
import com.app.hack_brain.repository.DatabaseRepository
import com.app.hack_brain.utils.liveData.SingleLiveData
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