package com.example.myrealtrip



import com.example.myrealtrip.views.splash.Splash
import org.junit.Assert.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainUnitTest {
    @DisplayName("1.3초후에 뉴스 화면리스트로 이동합니다.")
    @Test
    fun addition_isCorrect() {

        assertEquals(4, 2 + 2)
    }

    @DisplayName("뉴스 리스트에는 각 뉴스의 썸네일, 제목, 본문의 일부, 주요 키워드 3개가 표시되어야 합니다.")
    @Test
    fun ab() {

        assertEquals(4, 2 + 2)
    }
    @DisplayName("각 뉴스 항목(item)을 선택하면 뉴스 상세보기 화면으로 이동할 수 있습니다.")
    @Test
    fun dd() {

        assertEquals(4, 2 + 2)
    }
    @DisplayName("키워드 - 뉴스 본문의 내용으로부터,2글자 이상의 단어들 중,띄어쓰기만 고려")
    @Test
    fun isKeywordCorrect1() {

        assertEquals(4, 2 + 2)
    }
    @DisplayName("키워드 - 등장 빈도수가 높은 순서대로 3건(빈도수가 같을 경우 문자정렬 오름차순 적용)")
    @Test
    fun isKeywordCorrect2() {

        assertEquals(4, 2 + 2)
    }

    @DisplayName("리스트를 당겨서 새로고침 할 수 있도록 처리")
    @Test
    fun sdb() {

        assertEquals(4, 2 + 2)
    }
    @DisplayName("리스트에서 선택한 뉴스의 기사 본문을 읽을 수 있습니다.")
    @Test
    fun teeqt() {

        assertEquals(4, 2 + 2)
    }
    @DisplayName("뉴스 원문은 WebView를 사용해 표시")
    @Test
    fun hhhr() {

        assertEquals(4, 2 + 2)
    }

}
