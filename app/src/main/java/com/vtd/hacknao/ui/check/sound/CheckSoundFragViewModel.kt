package com.vtd.hacknao.ui.check.sound

import com.vtd.hacknao.common.base.BaseViewModel
import com.vtd.hacknao.repository.DatabaseRepository
import com.vtd.hacknao.data.local.entity.VocabularyEntity
import com.vtd.hacknao.utils.liveData.SingleLiveData

class CheckSoundFragViewModel(private val dbRepository: DatabaseRepository) : BaseViewModel() {
    val vocabularyListEvent = SingleLiveData<List<VocabularyEntity>>()
    val updateProgressSuccess = SingleLiveData<Boolean>()

    fun getVocabularyOfUnit(unit: Int) {
        viewModelScope(vocabularyListEvent) {
            dbRepository.getVocabularyOfUnit(unit)
        }
    }

    fun updateProcess(unit: Int, progress: Int) {
        viewModelScope {
            dbRepository.updateSoundProgress(unit, progress).executeIfSucceed {
                updateProgressSuccess.value = true
            }.executeIfFailed {
                updateProgressSuccess.value = false
            }
        }
    }
}