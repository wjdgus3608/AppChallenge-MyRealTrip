package com.example.myrealtrip.utils.RssUtil

import android.util.Log
import com.example.myrealtrip.model.NewsItem
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

object RssParser {
    fun parse(stream:InputStream):ArrayList<NewsItem>{
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
                        isItemFound=false
                        list.add(newsItem!!)
//                        newsItem=NewsItem()
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
        return list
    }
}