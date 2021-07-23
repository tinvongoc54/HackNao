package com.app.hack_brain.ui.timer.dialog

import com.app.hack_brain.common.base.BaseViewModel
import com.app.hack_brain.data.local.entity.TimerEntity
import com.app.hack_brain.repository.DatabaseRepository

class ChooseTimerFragViewModel(private val dbRepository: DatabaseRepository) : BaseViewModel() {

    fun insertTimer(timer: TimerEntity) {
        viewModelScope(isShowLoading = true) {
            dbRepository.insertTimer(timer)
        }
    }

    fun updateTimer(timer: TimerEntity) {
        viewModelScope(isShowLoading = true) {
            dbRepository.updateTimer(timer)
        }
    }
}