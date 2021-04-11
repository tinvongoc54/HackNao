package com.app.hack_brain.ui.check

import com.app.hack_brain.common.base.BaseViewModel
import com.app.hack_brain.data.local.db.DatabaseHelper
import com.app.hack_brain.data.local.entity.VocabularyEntity
import timber.log.Timber

class CheckFragViewModel(private val dbHelper: DatabaseHelper) : BaseViewModel() {

    fun insert() {
        viewModelScope {
            dbHelper.insertVocabulary(
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

    fun getVoc() {

        Timber.i("start")
        viewModelScope {
            val db = dbHelper.getAllVocabulary()
            Timber.i("size: ${db.getResultData()?.size}")
            db.getResultData()?.forEach {
                Timber.i("voc: ${it.word}")
            }
            db
        }
    }
}