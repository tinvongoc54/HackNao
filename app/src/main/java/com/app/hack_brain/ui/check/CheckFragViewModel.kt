package com.app.hack_brain.ui.check

import com.app.hack_brain.common.base.BaseViewModel
import com.app.hack_brain.repository.DatabaseRepository
import com.app.hack_brain.data.local.entity.UnitEntity
import com.app.hack_brain.data.local.entity.VocabularyEntity
import com.app.hack_brain.utils.liveData.SingleLiveData

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