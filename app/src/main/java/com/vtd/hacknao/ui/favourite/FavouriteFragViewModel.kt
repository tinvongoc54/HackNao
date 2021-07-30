package com.vtd.hacknao.ui.favourite

import com.vtd.hacknao.common.base.BaseViewModel
import com.vtd.hacknao.repository.DatabaseRepository
import com.vtd.hacknao.data.local.entity.VocabularyEntity
import com.vtd.hacknao.utils.liveData.SingleLiveData

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