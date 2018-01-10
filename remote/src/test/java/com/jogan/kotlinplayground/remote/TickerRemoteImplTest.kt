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
package com.jogan.kotlinplayground.remote

import com.jogan.kotlinplayground.data.model.TickerEntity
import com.jogan.kotlinplayground.remote.mapper.TickerEntityMapper
import com.jogan.kotlinplayground.remote.test.factory.TickerFactory
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString

@RunWith(JUnit4::class)
class TickerRemoteImplTest {

    private lateinit var entityMapper: TickerEntityMapper
    private lateinit var coinMarketService: CoinMarketService

    private lateinit var tickerRemoteImpl: TickerRemoteImpl

    @Before
    fun setup() {
        entityMapper = mock()
        coinMarketService = mock()
        tickerRemoteImpl = TickerRemoteImpl(coinMarketService, entityMapper)
    }

    @Test
    fun getTickerForCurrencyCompletes() {
        stubCoinMarkertServiceGetTickerForCurrency(Flowable.just(TickerFactory.makeTickerResponse()))
        val testObserver = tickerRemoteImpl.getTickerForCurrency("bitcoin").test()
        testObserver.assertComplete()
    }

    @Test
    fun getTickerForCurrencyReturnsData() {
        val tickerResponse = TickerFactory.makeTickerResponse()
        stubCoinMarkertServiceGetTickerForCurrency(Flowable.just(tickerResponse))
        val tickerEntities = mutableListOf<TickerEntity>()
        tickerResponse.ticker.forEach {
            tickerEntities.add(entityMapper.mapFromRemote(it))
        }

        val testObserver = tickerRemoteImpl.getTickerForCurrency("bitcoin").test()
        testObserver.assertValue(tickerEntities)
    }

    private fun stubCoinMarkertServiceGetTickerForCurrency(observable: Flowable<CoinMarketService.TickerResponse>) {
        whenever(coinMarketService.getTickerForCurrency(anyString()))
                .thenReturn(observable)
    }
}