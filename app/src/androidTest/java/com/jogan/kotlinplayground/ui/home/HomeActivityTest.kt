/*
 * Copyright 2017 John Hogan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jogan.kotlinplayground.ui.home

import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.jogan.kotlinplayground.data.model.Ticker
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
class HomeActivityTest {
    @Rule
    @JvmField
    val activity = ActivityTestRule<HomeActivity>(HomeActivity::class.java, false, false)

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