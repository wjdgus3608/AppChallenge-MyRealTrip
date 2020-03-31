package com.example.myrealtrip

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.myrealtrip.views.listview.NewsAdapter
import com.example.myrealtrip.views.main.MainActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule
import java.util.concurrent.TimeUnit

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
//메인 액티비티 UI 테스트
class MainActivityUiTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    //뉴스 리스트 보여지는지 확인
    fun isNewListDisplayed() {
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    //뉴스 클릭시 뉴스 상세보기 화면의 각 뷰 존재여부 확인
    fun newsDetailFragmentCheck() {
        TimeUnit.MILLISECONDS.sleep(5000)
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<NewsAdapter.NewsViewHolder>(0, click()))
        onView(withId(R.id.detail_view)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_title)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_keywords)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_webview)).check(matches(isDisplayed()))
    }
}
