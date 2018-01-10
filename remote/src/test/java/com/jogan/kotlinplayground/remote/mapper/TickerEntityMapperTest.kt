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
package com.jogan.kotlinplayground.remote.mapper

import com.jogan.kotlinplayground.remote.test.factory.TickerFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class TickerEntityMapperTest {
    private lateinit var tickerEntityMapper: TickerEntityMapper

    @Before
    fun setUp() {
        tickerEntityMapper = TickerEntityMapper()
    }

    @Test
    fun mapFromRemoteMapsData() {
        val tickerModel = TickerFactory.makeTickerModel()
        val tickerEntity = tickerEntityMapper.mapFromRemote(tickerModel)

        assertEquals(tickerModel.id, tickerEntity.id)
        assertEquals(tickerModel.name, tickerEntity.name)
        assertEquals(tickerModel.percent_change_1h, tickerEntity.percentChange1h)
        assertEquals(tickerModel.percent_change_24h, tickerEntity.percentChange24h)
        assertEquals(tickerModel.percent_change_7d, tickerEntity.percentChange7d)
        assertEquals(tickerModel.price_btc, tickerEntity.priceBtc)
        assertEquals(tickerModel.price_usd, tickerEntity.priceUsd)
        assertEquals(tickerModel.rank, tickerEntity.rank)
        assertEquals(tickerModel.symbol, tickerEntity.symbol)
    }
}