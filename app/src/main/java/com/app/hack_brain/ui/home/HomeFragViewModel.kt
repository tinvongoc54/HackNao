package com.app.hack_brain.ui.home

import androidx.lifecycle.MutableLiveData
import com.app.hack_brain.common.base.BaseViewModel
import com.app.hack_brain.repository.DatabaseRepository
import com.app.hack_brain.data.local.entity.VocabularyEntity

class HomeFragViewModel(private val dbRepository: DatabaseRepository) : BaseViewModel() {
    val randomVoc = MutableLiveData<List<VocabularyEntity>>()

    fun getRandomVoc() {
        viewModelScope(randomVoc) {
            dbRepository.getRandomVocabulary()
        }
    }
}