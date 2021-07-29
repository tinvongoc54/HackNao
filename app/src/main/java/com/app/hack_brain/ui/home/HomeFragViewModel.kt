package com.app.hack_brain.ui.home

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.app.hack_brain.common.Constant
import com.app.hack_brain.common.base.BaseViewModel
import com.app.hack_brain.data.local.entity.TargetEntity
import com.app.hack_brain.repository.DatabaseRepository
import com.app.hack_brain.data.local.entity.VocabularyEntity
import com.app.hack_brain.data.local.sharedpfers.SharePrefsService
import com.app.hack_brain.extension.convertTimestampToDate
import com.app.hack_brain.extension.toDate
import com.app.hack_brain.utils.liveData.SingleLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import java.util.*
import java.util.concurrent.TimeUnit

class HomeFragViewModel(private val dbRepository: DatabaseRepository, private val sharePrefsService: SharePrefsService) : BaseViewModel() {
    val randomVoc = MutableLiveData<List<VocabularyEntity>>()
    val targetList = MutableLiveData<List<TargetEntity>>()
    val target = SingleLiveData<TargetEntity>()
    val positionTargetScroll = SingleLiveData<Int>()
    val positionAutoScroll = SingleLiveData<Int>()

    fun getRandomVoc() {
        viewModelScope(randomVoc) {
            dbRepository.getRandomVocabulary()
        }
    }

    fun getTargetUnit() {
        viewModelScope {
            dbRepository.getTargetList().executeIfSucceed {
                kotlin.run {
                    it.forEachIndexed { index, target ->
                        if (Date().after(toDate(target.date)) && convertTimestampToDate(System.currentTimeMillis()) != target.date) {
                            if (target.status == 1) {
                                target.status = 0
                                dbRepository.updateTargetByUnit(target.unit ?: 0, 0)
                            }
                        } else {
                            positionTargetScroll.value = index
                            return@run
                        }
                    }
                }
                targetList.value = it
            }
        }
    }

    fun updateTarget(target: TargetEntity) {
        viewModelScope {
            dbRepository.updateTarget(target)
        }
    }

    fun getCurrentTarget() {
        val date = convertTimestampToDate(System.currentTimeMillis())
        viewModelScope(target) {
            dbRepository.getTarget(date)
        }
    }

    fun getUnitNumber(): Int {
        return sharePrefsService.getTargetUnitNumberOfDay()
    }

    fun setUnitNumber(number: Int) {
        sharePrefsService.setTargetUnitNumberOfDay(number)
    }

    fun changeTarget(id: Int, unitNumber: Int) {
        val calendar = Calendar.getInstance()
        var temp = 0
        for (i in id..Constant.TOTAL_UNIT) {
            if (temp == unitNumber) {
                temp = 0
                calendar.add(Calendar.DATE, 1)
            }
            updateTarget(TargetEntity(i, i, convertTimestampToDate(calendar.timeInMillis), 1))
            temp++
        }
        Handler().postDelayed({
            getTargetUnit()
        }, 500)
    }

    fun changeStatusTargetOfUnit(unit: Int) {
        viewModelScope {
            dbRepository.getUnit(unit).executeIfSucceed {
                if (it.isEnableNextUnit()) {
                    dbRepository.updateTargetByUnit(it.unit ?: 0, 2)
                }
            }
        }
    }

    fun autoScrollVocabulary() {
        Flowable.interval(5, TimeUnit.SECONDS)
            .map { it % 5 }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                positionAutoScroll.value = it.toInt()
            }
    }

    fun setIsOpenedApp(isOpened: Boolean) {
        sharePrefsService.setIsOpenedApp(isOpened)
    }

    fun isOpenedApp() = sharePrefsService.isOpenedApp()
}