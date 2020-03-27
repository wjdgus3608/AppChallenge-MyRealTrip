package com.example.myrealtrip.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myrealtrip.model.NewsItem
import com.example.myrealtrip.utils.rssutil.RssConnector

class MainViewModel : ViewModel(){
    var frgMode=MutableLiveData<Int>()
    var mList=MutableLiveData<ArrayList<NewsItem>>()
    var selectedNews=MutableLiveData<NewsItem?>()
    var isLoading=MutableLiveData<Boolean>()
    var isWebViewLoading=MutableLiveData<Boolean>()
    init {
        frgMode.postValue(0)
        val list=ArrayList<NewsItem>()
        mList.postValue(list)
        selectedNews.value=null
        isLoading.value=false
        isWebViewLoading.value=false
    }

    fun requestData(url:String){
        isLoading.postValue(true)
        RssConnector(url,this).start()
    }

}