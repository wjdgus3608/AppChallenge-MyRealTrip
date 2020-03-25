package com.example.myrealtrip.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myrealtrip.model.NewsItem
import com.example.myrealtrip.utils.rssutil.RssConnector

class MainViewModel : ViewModel(){
    var frgMode=MutableLiveData<Int>()
    var mList=MutableLiveData<ArrayList<NewsItem>>()
    var selectedNews=MutableLiveData<NewsItem?>()
    var isRefreshing=MutableLiveData<Boolean>()
    init {
        frgMode.postValue(0)
        val list=ArrayList<NewsItem>()
        mList.postValue(list)
        selectedNews.value=null
        isRefreshing.value=false
    }

    fun refreshData(){
        isRefreshing.postValue(true)
        RssConnector("https://news.google.com/rss?hl=ko&gl=KR&ceid=KR:ko",this).start()
    }
}