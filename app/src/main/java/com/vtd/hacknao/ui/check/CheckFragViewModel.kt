package com.vtd.hacknao.ui.check

import com.vtd.hacknao.common.base.BaseViewModel
import com.vtd.hacknao.repository.DatabaseRepository
import com.vtd.hacknao.data.local.entity.UnitEntity
import com.vtd.hacknao.data.local.entity.VocabularyEntity
import com.vtd.hacknao.utils.liveData.SingleLiveData

class CheckFragViewModel(private val dbRepository: DatabaseRepository) : BaseViewModel() {

    val unitListEvent = SingleLiveData<List<UnitEntity>>()
    fun insert() {
        viewModelScope {
            dbRepository.insertVocabulary(
                VocabularyEntity(
                    id = 1,
                    word = "abc",
                    phonetic = "abcc",
                    meanings = "cccc",
                    note = "asd",
                    isFavourite = 1,
                    isLearnedEngVie = 1,
                    isLearnedSound = 1,
                    isLearnedVieEng = 1,
                    shortMean = "ccasdf"
                )
            )
        }
    }

    fun getUnitList() {
        viewModelScope(unitListEvent) {
            dbRepository.getUnitList()
        }
    }
}