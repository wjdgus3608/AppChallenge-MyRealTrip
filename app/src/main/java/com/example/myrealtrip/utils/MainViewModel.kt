package com.example.myrealtrip.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myrealtrip.model.NewsItem
import com.example.myrealtrip.utils.rssutil.RssConnector
import com.example.myrealtrip.utils.rssutil.RssParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlin.coroutines.Continuation
import kotlin.coroutines.suspendCoroutine

class MainViewModel : ViewModel(){
    var frgMode=MutableLiveData<Int>()
    var mList=MutableLiveData<ArrayList<NewsItem>>()
    var selectedNews=MutableLiveData<NewsItem?>()
    var isLoading=MutableLiveData<Boolean>()
    var isWebViewLoading=MutableLiveData<Boolean>()
    var continuation=MutableLiveData<Continuation<String>?>()
    var connectJob:Job?=null
    var parsingJob:Job?=null
    init {
        frgMode.postValue(0)
        val list=ArrayList<NewsItem>()
        mList.postValue(list)
        selectedNews.value=null
        isLoading.value=false
        isWebViewLoading.value=false
    }

    fun requestData(url:String){
        parsingJob?.cancel()
        connectJob?.cancel()
        parsingJob=null
        connectJob=null
        isLoading.postValue(true)
        RssConnector(url,this).startCoroutine()
    }

    suspend fun pauseCoroutine() = suspendCoroutine<String> { continuation.postValue(it) }

    override fun onCleared() {
        super.onCleared()
    }
}