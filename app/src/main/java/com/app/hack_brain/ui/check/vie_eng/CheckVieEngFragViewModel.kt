package com.app.hack_brain.ui.check.vie_eng

import com.app.hack_brain.common.base.BaseViewModel
import com.app.hack_brain.data.local.db.DatabaseRepository
import com.app.hack_brain.data.local.entity.VocabularyEntity
import com.app.hack_brain.utils.liveData.SingleLiveData

class CheckVieEngFragViewModel(private val dbRepository: DatabaseRepository) : BaseViewModel() {
    val vocabularyListEvent = SingleLiveData<List<VocabularyEntity>>()
    val updateProgressSuccess = SingleLiveData<Boolean>()

    fun getVocabularyOfUnit(unit: Int) {
        viewModelScope(vocabularyListEvent) {
            dbRepository.getVocabularyOfUnit(unit)
        }
    }

    fun updateProcess(unit: Int, progress: Int) {
        viewModelScope {
            dbRepository.updateVieEngProgress(unit, progress).executeIfSucceed {
                updateProgressSuccess.value = true
            }.executeIfFailed {
                updateProgressSuccess.value = false
            }
        }
    }
}