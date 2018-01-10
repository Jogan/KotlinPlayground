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
package com.jogan.kotlinplayground.data.source

import com.jogan.kotlinplayground.data.model.TickerEntity
import com.jogan.kotlinplayground.data.repository.TickerRemote
import com.jogan.kotlinplayground.data.test.factory.TickerFactory
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString

@RunWith(JUnit4::class)
class TickerRemoteDataStoreTest {

    private lateinit var tickerRemoteDataStore: TickerRemoteDataStore

    private lateinit var tickerRemote: TickerRemote

    @Before
    fun setUp() {
        tickerRemote = mock()
        tickerRemoteDataStore = TickerRemoteDataStore(tickerRemote)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun clearTickersThrowsException() {
        tickerRemoteDataStore.clearTickers().test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun saveTickerForCurrencyThrowsException() {
        tickerRemoteDataStore.saveTicker("bitcoin", TickerFactory.makeTickerEntityList(1)).test()
    }

    @Test
    fun getTickerForCurrencyCompletes() {
        stubTickerCacheGetTickerForCurrency(Flowable.just(TickerFactory.makeTickerEntityList(1)))
        val testObserver = tickerRemote.getTickerForCurrency("bitcoin").test()
        testObserver.assertComplete()
    }

    private fun stubTickerCacheGetTickerForCurrency(single: Flowable<List<TickerEntity>>) {
        whenever(tickerRemote.getTickerForCurrency(anyString())).thenReturn(single)
    }
}