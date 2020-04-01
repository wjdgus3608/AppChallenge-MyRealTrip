package com.example.myrealtrip.model

data class NewsItem(
    var title: String = "",
    var des: String = "",
    var url: String = "",
    var keywords: ArrayList<String>? = null,
    var img: String = ""
)