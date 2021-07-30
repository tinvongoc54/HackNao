package com.vtd.hacknao.ui.check.eng_vie

import com.vtd.hacknao.common.base.BaseViewModel
import com.vtd.hacknao.repository.DatabaseRepository
import com.vtd.hacknao.data.local.entity.VocabularyEntity
import com.vtd.hacknao.utils.liveData.SingleLiveData

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