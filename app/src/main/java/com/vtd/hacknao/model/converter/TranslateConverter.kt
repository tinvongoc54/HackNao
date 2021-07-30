package com.vtd.hacknao.model.converter

import com.vtd.hacknao.data.remote.response.DetailTranslateResponse
import com.vtd.hacknao.data.remote.response.TranslateResponse
import com.vtd.hacknao.model.uimodel.DetailTranslate

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