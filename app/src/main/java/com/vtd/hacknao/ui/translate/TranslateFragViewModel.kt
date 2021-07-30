package com.vtd.hacknao.ui.translate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vtd.hacknao.common.base.BaseViewModel
import com.vtd.hacknao.data.remote.response.TranslateResponse
import com.vtd.hacknao.model.converter.TranslateConverter
import com.vtd.hacknao.model.uimodel.DetailTranslate
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

class TranslateFragViewModel(private val okHttpClient: OkHttpClient) : BaseViewModel() {
    val sentencesList = MutableLiveData<List<DetailTranslate>>()

    fun doTranslate(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val client = okHttpClient
            val request = Request.Builder()
                .url("http://api.tracau.vn/WBBcwnwQpV89/{$text}/en/JSON_CALLBACK")
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val result = response.body?.string()?.replace("JSON_CALLBACK(", "")?.dropLast(3)
                    val translateResponse = Gson().fromJson(result, TranslateResponse::class.java)
                    sentencesList.postValue(
                        TranslateConverter.responseToListDetailTranslate(
                            translateResponse
                        )
                    )
                }

                override fun onFailure(call: Call, e: IOException) {

                }
            })
        }
    }
}