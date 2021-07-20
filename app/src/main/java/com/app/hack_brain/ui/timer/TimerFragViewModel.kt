package com.app.hack_brain.ui.timer

import androidx.lifecycle.MutableLiveData
import com.app.hack_brain.common.Constant
import com.app.hack_brain.common.base.BaseViewModel
import com.app.hack_brain.data.local.entity.TimerEntity
import com.app.hack_brain.repository.DatabaseRepository

class TimerFragViewModel(private val dbRepository: DatabaseRepository) : BaseViewModel() {
    val openAppTimerList = MutableLiveData<List<TimerEntity>>()
    val remindVocTimerList = MutableLiveData<List<TimerEntity>>()

    fun getTimerList() {
        viewModelScope {
            dbRepository.getTimerList().executeIfSucceed {
                openAppTimerList.value = it.filter { timer -> timer.type == Constant.TYPE_OPEN_APP }
                remindVocTimerList.value = it.filter { timer -> timer.type == Constant.TYPE_REMIND_VOCABULARY }
            }
        }
    }
}