package com.example.myrealtrip.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myrealtrip.model.NewsItem

class MainViewModel : ViewModel(){
    var frgMode=MutableLiveData<Int>()
    var mList=MutableLiveData<ArrayList<NewsItem>>()
    var selectedNews=MutableLiveData<NewsItem?>()
    init {
        frgMode.postValue(0)
        var list=ArrayList<NewsItem>()
        list.add(NewsItem(0,"title 1","des 1"))
        list.add(NewsItem(0,"title 2","des 2"))
        list.add(NewsItem(0,"title 3","des 3"))
        mList.postValue(list)
        selectedNews.value=null
    }

}