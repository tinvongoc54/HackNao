package com.app.hack_brain.ui.check.eng_vie

import com.app.hack_brain.common.base.BaseViewModel
import com.app.hack_brain.repository.DatabaseRepository
import com.app.hack_brain.data.local.entity.VocabularyEntity
import com.app.hack_brain.utils.liveData.SingleLiveData

class CheckEngVieFragViewModel(private val dbRepository: DatabaseRepository) : BaseViewModel() {
    val vocabularyListEvent = SingleLiveData<List<VocabularyEntity>>()
    val updateProgressEvent = SingleLiveData<Boolean>()

    fun getVocabularyOfUnit(unit: Int) {
        viewModelScope(vocabularyListEvent) {
            dbRepository.getVocabularyOfUnit(unit)
        }
    }

    fun updateProcess(unit: Int, progress: Int) {
        viewModelScope {
            dbRepository.updateEngVieProgress(unit, progress).executeIfSucceed {
                updateProgressEvent.value = true
            }.executeIfFailed {
                updateProgressEvent.value = false
            }
        }
    }

    fun setFavouriteVoc(id: Int, isFavourite: Boolean) {
        viewModelScope(isShowLoading = false) {
            dbRepository.setFavouriteVoc(id, isFavourite)
        }
    }
}