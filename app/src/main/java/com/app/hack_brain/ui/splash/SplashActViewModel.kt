package com.app.hack_brain.ui.splash

import androidx.lifecycle.viewModelScope
import com.app.hack_brain.common.base.BaseViewModel
import com.app.hack_brain.repository.DatabaseRepository
import com.app.hack_brain.utils.liveData.SingleLiveData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActViewModel(private val dbRepository: DatabaseRepository) : BaseViewModel() {
    var validToken = SingleLiveData<Boolean>()

    fun refreshToken() {
        viewModelScope.launch {
            delay(1500)
            validToken.value = true
        }
    }
}