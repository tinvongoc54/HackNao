package com.app.hack_brain.model.converter

import com.app.hack_brain.data.local.entity.VocabularyEntity
import com.app.hack_brain.extension.nullToBlank
import com.app.hack_brain.model.uimodel.Word

object WordConverter {
    fun convertToWordList(list: List<VocabularyEntity>): MutableList<Word> {
        val result = mutableListOf<Word>()
        list.forEach {
            result.add(convertToWord(it))
        }
        return result
    }

    private fun convertToWord(vocabulary: VocabularyEntity): Word {
        return Word(
            id = vocabulary.id,
            word = vocabulary.word.nullToBlank(),
            phonetic = vocabulary.phonetic.nullToBlank(),
            meanings = vocabulary.shortMean.nullToBlank()
        )
    }
}