package com.example.myrealtrip

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.myrealtrip.views.splash.SplashActivity

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
//스플래시 UI 테스트
class SplashActivityActivityUiTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(SplashActivity::class.java)

    @Test
    //스플래시 화면 1.3초간만 보이기
    fun isSplashDisplayedInTime() {
        onView(withId(R.id.splash_layout)).check(matches(isDisplayed()))
        TimeUnit.MILLISECONDS.sleep(1300)
        onView(withId(R.id.splash_layout)).check(doesNotExist())
    }
    @Test
    //스플래시 화면에 로고 3개, 설명, 버전 정보 있는지 확인
    fun isSplashHasAllView() {
        onView(withId(R.id.splash_imageview1)).check(matches(isDisplayed())) //
        onView(withId(R.id.splash_imageview2)).check(matches(isDisplayed()))
        onView(withId(R.id.splash_imageview3)).check(matches(isDisplayed()))
        onView(withId(R.id.splash_des)).check(matches(isDisplayed()))
        onView(withId(R.id.splash_version)).check(matches(isDisplayed()))
    }
}
