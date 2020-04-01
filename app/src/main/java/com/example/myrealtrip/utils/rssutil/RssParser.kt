package com.example.myrealtrip.utils.rssutil

import android.util.Log
import com.example.myrealtrip.model.NewsItem
import com.example.myrealtrip.utils.keywordutil.KeyWordParser
import com.example.myrealtrip.viewmodel.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import org.jsoup.Jsoup
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream
import java.net.SocketTimeoutException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.*
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object RssParser {
    private const val loadSize: Int = 10
    suspend fun parse(stream: InputStream, model: MainViewModel) {
        val list = Vector<NewsItem>()
        val factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = true
        val parser = factory.newPullParser()
        parser.setInput(stream, null)
        var eventType = parser.eventType
        var newsItem = NewsItem()
        var isItemFound = false
        var text = ""

        allSSLTrust()

        while (eventType != XmlPullParser.END_DOCUMENT) {
            yield()
            val tagName = parser.name

            when (eventType) {

                XmlPullParser.START_TAG -> {
                    if (tagName.equals("item", true)) {
                        isItemFound = true
                        newsItem = NewsItem()
                    }
                }

                XmlPullParser.TEXT -> text = parser.text

                XmlPullParser.END_TAG -> {
                    if (tagName.equals("item", true)) {
                        if (newsItem.url.isNotEmpty()) {
                            val url = newsItem.url
                            val title = newsItem.title
                            val job = GlobalScope.launch {
                                try {
                                    val meta =
                                        Jsoup.connect(url).ignoreHttpErrors(true).get()
                                            .select("meta")
                                    val img = meta.select("[property=og:image]").attr("content")
                                    val des =
                                        meta.select("[property=og:description]").attr("content")
                                    Log.d("log", "title : $title")
                                    val tmpItem = NewsItem()
                                    tmpItem.title = title
                                    tmpItem.url = url
                                    tmpItem.img = img.toString()
                                    tmpItem.des = des.toString()
                                    tmpItem.keywords = KeyWordParser.parseKeyWord(tmpItem.des)
                                    isItemFound = false
                                    list.add(tmpItem)
                                    model.mList.postValue(list)
                                } catch (e: SocketTimeoutException) {
                                    Log.e("log", "time out at : $url")
                                }
                                catch (e:IOException){
                                    Log.e("log", "$e at : $url")
                                }
                            }
                            model.parsingJob.add(job)
                            yield()
                            if (model.parsingJob.size == loadSize) {
                                model.parsingJob.map { joinAll(it) }
                                model.isLoading.postValue(false)
                                model.pauseCoroutine()
                                model.clearParsingJob()
                            }
                        }
                    } else if (isItemFound && tagName.equals("title", true)) {
                        newsItem.title = text
                    } else if (isItemFound && tagName.equals("link", true)) {
                        newsItem.url = text
                    }
                }
            }
            eventType = parser.next()
        }
        model.parsingJob.map { joinAll(it) }
        model.mList.postValue(list)
        model.isLoading.postValue(false)
        model.clearParsingJob()
    }

    fun allSSLTrust() {
        val trustAllCerts: Array<TrustManager> =
            arrayOf(object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate>? {
                    return null
                }

                override fun checkClientTrusted(
                    certs: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun checkServerTrusted(
                    certs: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }
            })

        val sc: SSLContext = SSLContext.getInstance("SSL")
        sc.init(null, trustAllCerts, SecureRandom())
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory())

    }

}