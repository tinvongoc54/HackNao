package com.vtd.hacknao.ui.timer.dialog

import com.vtd.hacknao.common.base.BaseViewModel
import com.vtd.hacknao.data.local.entity.TimerEntity
import com.vtd.hacknao.repository.DatabaseRepository

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