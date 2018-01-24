package com.jogan.kotlinplayground.ui.main

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.jogan.kotlinplayground.R
import com.jogan.kotlinplayground.data.model.Ticker
import com.jogan.kotlinplayground.test.TestApplication
import com.jogan.kotlinplayground.test.util.TickerFactory
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    val activity = ActivityTestRule<MainActivity>(MainActivity::class.java, false, false)

    @Test
    fun activityLaunches() {
        stubTickerRepositoryLoadTickerForCurrency(Single.just(TickerFactory.makeTicker()))
        activity.launchActivity(null)
    }

    @Test
    fun tickerIsDisplayed() {
        val ticker = TickerFactory.makeTicker()
        stubTickerRepositoryLoadTickerForCurrency(Single.just(ticker))
        activity.launchActivity(null)

        onView(ViewMatchers.withId(R.id.tickerText)).check(matches(withText(ticker.priceUsd)))
    }

    private fun stubTickerRepositoryLoadTickerForCurrency(single: Single<Ticker>) {
        whenever(TestApplication.appComponent().tickerRepository().getTickerForCurrency(anyString()))
                .thenReturn(single)
    }
}