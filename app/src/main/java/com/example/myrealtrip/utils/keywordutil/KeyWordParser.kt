package com.example.myrealtrip.utils.keywordutil

import android.util.Log
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

object KeyWordParser{
    fun parseKeyWord(des:String):ArrayList<String>{
        val reg=Regex("[^ㄱ-ㅎ|가-힣|a-z|A-Z|0-9]")
        val parsedList=des.split(" ").map { it.replace(reg,"") }.filter { it.length>=2 }
        val keyWordMap=HashMap<String,Int>()
        parsedList.map {
            if(keyWordMap.containsKey(it)){
                val value=keyWordMap[it]
                keyWordMap[it]=value!!.plus(1)
            }
            else keyWordMap[it]=1
        }
        val keyWordList=ArrayList<String>()
        val tmpList=keyWordMap.toList().sortedWith(Comparator{ o1, o2 ->
            if (o1.second==o2.second) o1.first.compareTo(o2.first) else o1.second.compareTo(o2.second)}).map { it.first }
        for(i in 0 until if(tmpList.size>3) 3 else tmpList.size)
            keyWordList.add(tmpList[i])
        return keyWordList
    }
}