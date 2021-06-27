package com.app.hack_brain.ui.favourite

import com.app.hack_brain.common.base.BaseViewModel
import com.app.hack_brain.data.local.db.DatabaseRepository
import com.app.hack_brain.data.local.entity.VocabularyEntity
import com.app.hack_brain.utils.liveData.SingleLiveData

class FavouriteFragViewModel(private val dbRepository: DatabaseRepository) : BaseViewModel() {
    val favouriteVocEvent = SingleLiveData<List<VocabularyEntity>>()

    fun getFavouriteVocList() {
        viewModelScope(favouriteVocEvent) {
            dbRepository.getFavouriteVocList()
        }
    }

    fun setUnFavouriteVoc(id: Int) {
        viewModelScope(isShowLoading = false) {
            dbRepository.setFavouriteVoc(id, false)
        }
    }
}