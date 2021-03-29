package com.app.hack_brain.model.converter

import com.app.hack_brain.data.remote.response.DetailTranslateResponse
import com.app.hack_brain.data.remote.response.TranslateResponse
import com.app.hack_brain.model.uimodel.DetailTranslate

object TranslateConverter {
    fun responseToListDetailTranslate(response: TranslateResponse): List<DetailTranslate> {
        val result = mutableListOf<DetailTranslate>()
        response.sentences.forEach {
            result.add(responseToDetailTranslate(it.fields))
        }
        return result
    }

    private fun responseToDetailTranslate(response: DetailTranslateResponse): DetailTranslate {
        return DetailTranslate(response.en, response.vi)
    }
}