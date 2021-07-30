package com.vtd.hacknao.ui.timer

import androidx.lifecycle.MutableLiveData
import com.vtd.hacknao.common.Constant
import com.vtd.hacknao.common.base.BaseViewModel
import com.vtd.hacknao.data.local.entity.TimerEntity
import com.vtd.hacknao.repository.DatabaseRepository

class TimerFragViewModel(private val dbRepository: DatabaseRepository) : BaseViewModel() {
    val openAppTimerList = MutableLiveData<List<TimerEntity>>()
    val remindVocTimerList = MutableLiveData<List<TimerEntity>>()
    val deleteSuccess = MutableLiveData<Boolean>()
    var newIdTimer = 0
    var deletePosition = 0

    fun getTimerList() {
        viewModelScope {
            dbRepository.getTimerList().executeIfSucceed {
                newIdTimer = (it.maxBy { timer -> timer.id }?.id ?: 0) + 1
                openAppTimerList.value = it.filter { timer -> timer.type == Constant.TYPE_OPEN_APP }
                remindVocTimerList.value =
                    it.filter { timer -> timer.type == Constant.TYPE_REMIND_VOCABULARY }
            }
        }
    }

    fun deleteTimer(timer: TimerEntity, isOpenApp: Boolean, position: Int) {
        viewModelScope(isShowLoading = false) {
            dbRepository.deleteTimer(timer).executeIfSucceed {
                deletePosition = position
                deleteSuccess.value = isOpenApp
            }
        }
    }

    fun updateTimer(timer: TimerEntity) {
        viewModelScope(isShowLoading = false) {
            dbRepository.updateTimer(timer)
        }
    }
}