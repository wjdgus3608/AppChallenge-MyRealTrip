package com.example.myrealtrip.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myrealtrip.model.NewsItem
import com.example.myrealtrip.utils.rssutil.RssConnector
import kotlinx.coroutines.Job
import java.util.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.suspendCoroutine

class MainViewModel : ViewModel() {
    var frgMode = MutableLiveData(0)
    var mList = MutableLiveData<Vector<NewsItem>>(Vector())
    var selectedNews = MutableLiveData<NewsItem?>(null)
    var isLoading = MutableLiveData(false)
    var isWebViewLoading = MutableLiveData(false)
    var continuation = MutableLiveData<Continuation<String>?>()
    var connectJob: Job? = null
    var parsingJob = Vector<Job>()
    var isDataEnd = MutableLiveData(false)
    var toastMsg = MutableLiveData<String>(null)

    fun requestData(url: String) {
        isDataEnd.postValue(false)
        clearParsingJob()
        connectJob?.cancel()
        connectJob = null
        isLoading.postValue(true)
        RssConnector(url, this).startCoroutine()
    }

    suspend fun pauseCoroutine() = suspendCoroutine<String> { continuation.postValue(it) }

    fun clearParsingJob() {
        for (job in parsingJob)
            job.cancel()
        parsingJob.clear()
    }

    override fun onCleared() {
        super.onCleared()
        clearParsingJob()
        connectJob?.cancel()
        Log.d("log", "ViewModel cleared!")
    }
}