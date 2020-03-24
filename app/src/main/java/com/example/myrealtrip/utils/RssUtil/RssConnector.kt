package com.example.myrealtrip.utils.RssUtil

import android.util.Log
import com.example.myrealtrip.model.NewsItem
import com.example.myrealtrip.utils.MainViewModel
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class RssConnector(url:String,parentModel: MainViewModel):Thread(){
    val mUrl= URL(url)
    val model=parentModel
    override fun run() {
        super.run()
        val connect = mUrl.openConnection() as HttpURLConnection
        connect.readTimeout = 8000
        connect.connectTimeout = 8000
        connect.requestMethod = "GET"
        connect.connect()

        val responseCode: Int = connect.responseCode
        Log.e("log","$responseCode")
        var newsList:ArrayList<NewsItem>? = null
        if(responseCode == 200){
            var stream:InputStream=connect.inputStream
            try {
                newsList = RssParser.parse(stream)
            }
            catch (e: IOException){
                e.printStackTrace()
            }
        }
        Log.e("log","list size : ${newsList?.size}")
        model.mList.postValue(newsList)
    }
}