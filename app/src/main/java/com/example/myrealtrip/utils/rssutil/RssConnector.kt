package com.example.myrealtrip.utils.rssutil

import android.util.Log
import com.example.myrealtrip.utils.MainViewModel
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class RssConnector(url:String,parentModel: MainViewModel):Thread(){
    val mUrl= URL(url)
    val model=parentModel
    override fun run() {
        val connect = mUrl.openConnection() as HttpURLConnection
        connect.readTimeout = 10000
        connect.connectTimeout = 10000
        connect.requestMethod = "GET"
        connect.connect()

        val responseCode: Int = connect.responseCode
        Log.e("log","$responseCode")
        if(responseCode == 200){
            val stream:InputStream=connect.inputStream
            try {
                RssParser.parse(stream,model)
            }
            catch (e: IOException){
                e.printStackTrace()
            }
        }
        model.isRefreshing.postValue(false)
    }

}