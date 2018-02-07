package com.jogan.kotlinplayground.ui.main

import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.jogan.kotlinplayground.data.ticker.Ticker
import com.jogan.kotlinplayground.test.TestApplication
import com.jogan.kotlinplayground.test.util.TickerFactory
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    val activity = ActivityTestRule<MainActivity>(MainActivity::class.java, false, false)

    @Test
    fun activityLaunches() {
        stubTickerRepositoryLoadTickerForCurrency(Single.just(TickerFactory.makeTickerList(5)))
        activity.launchActivity(null)
    }

//    FIXME
//    @Test
//    fun tickerIsDisplayed() {
//        val ticker = TickerFactory.makeTicker()
//        stubTickerRepositoryLoadTickerForCurrency(Single.just(ticker))
//        activity.launchActivity(null)
//
//        onView(ViewMatchers.withId(R.id.tickerText)).check(matches(withText(ticker.priceUsd)))
//    }

    private fun stubTickerRepositoryLoadTickerForCurrency(single: Single<List<Ticker>>) {
        whenever(TestApplication.appComponent().tickerRepository().getTickers(anyInt(), anyInt()))
                .thenReturn(single)
    }
}