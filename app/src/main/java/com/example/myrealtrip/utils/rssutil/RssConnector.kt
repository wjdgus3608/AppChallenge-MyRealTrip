package com.example.myrealtrip.utils.rssutil

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import com.example.myrealtrip.utils.MainViewModel
import kotlinx.coroutines.*
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.suspendCoroutine

class RssConnector(url:String,parentModel: MainViewModel){
    private val mUrl= URL(url)
    val model=parentModel

    fun startCoroutine() {
        model.connectJob=GlobalScope.launch{
            doConnect()
        }
    }

     private suspend fun doConnect(){
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
                model.parsingJob?.join()
            }
            catch (e: IOException){
                e.printStackTrace()
            }
        }
        Log.e("log","background finish!")
    }


}