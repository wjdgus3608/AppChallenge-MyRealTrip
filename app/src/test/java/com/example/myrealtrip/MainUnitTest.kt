package com.example.myrealtrip



import android.util.Log
import com.example.myrealtrip.model.NewsItem
import com.example.myrealtrip.utils.keywordutil.KeyWordParser
import com.example.myrealtrip.viewmodel.MainViewModel
import com.example.myrealtrip.views.listview.NewsAdapter
import org.junit.Assert.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainUnitTest {
    @DisplayName("키워드 - 뉴스 본문의 내용으로부터,2글자 이상의 단어들 중,띄어쓰기만 고려")
    @Test
    fun isKeywordCorrect1() {
        val list=KeyWordParser.parseKeyWord("가 가 가 나나나나 다다 다다 라라")
        assertEquals("다다", list[0])
        assertEquals("나나나나", list[1])
        assertEquals("라라", list[2])
    }
    @DisplayName("키워드 - 등장 빈도수가 높은 순서대로 3건(빈도수가 같을 경우 문자정렬 오름차순 적용)")
    @Test
    fun isKeywordCorrect2() {
        val list=KeyWordParser.parseKeyWord("중복3 중복3 1중복 1중복 2중복 2중복 2중복 중복4")
        assertEquals(3, list.size)
        assertEquals("2중복", list[0])
        assertEquals("1중복", list[1])
        assertEquals("중복3", list[2])
    }
}
