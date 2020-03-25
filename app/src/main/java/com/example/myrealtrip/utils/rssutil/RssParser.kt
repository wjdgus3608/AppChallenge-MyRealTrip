package com.example.myrealtrip.utils.rssutil

import android.util.Log
import com.example.myrealtrip.model.NewsItem
import com.example.myrealtrip.utils.MainViewModel
import com.example.myrealtrip.utils.keywordutil.KeyWordParser
import org.jsoup.Jsoup
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import kotlin.collections.ArrayList

object RssParser {
    fun parse(stream:InputStream,model:MainViewModel){
        var list=ArrayList<NewsItem>()
        val factory=XmlPullParserFactory.newInstance()
        factory.isNamespaceAware=true
        val parser=factory.newPullParser()
        parser.setInput(stream,null)
        var eventType = parser.eventType
        var newsItem:NewsItem?=null
        var isItemFound=false
        var text=""
        while (eventType !=XmlPullParser.END_DOCUMENT){
            val tagName=parser.name
            when(eventType){
                XmlPullParser.START_TAG->{
                    if(tagName.equals("item",true)){
                        isItemFound=true
                        newsItem= NewsItem()
                    }
                }
                XmlPullParser.TEXT->{text=parser.text}
                XmlPullParser.END_TAG->{
                    if(tagName.equals("item",true)){
                        val meta=Jsoup.connect(newsItem!!.url).get().select("meta")
                        val img=meta.select("[property=og:image]").attr("content")
                        val des=meta.select("[property=og:description]").attr("content")
                        Log.e("log",des.toString())
                        newsItem.img=img.toString()
                        newsItem.des=des.toString()
                        newsItem.keywords=KeyWordParser.parseKeyWord(newsItem.des)
                        isItemFound=false
                        list.add(newsItem)
                        model.mList.postValue(list)
                    }
                    else if(isItemFound && tagName.equals("title",true)){
                        newsItem?.title=text
                    }
                    else if(isItemFound && tagName.equals("link",true)){
                        newsItem?.url=text
                    }
                }
            }
            eventType=parser.next()
        }
    }
}