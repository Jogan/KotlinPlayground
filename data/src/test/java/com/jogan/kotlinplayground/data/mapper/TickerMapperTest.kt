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
package com.jogan.kotlinplayground.data.mapper

import com.jogan.kotlinplayground.data.model.TickerEntity
import com.jogan.kotlinplayground.data.test.factory.TickerFactory
import com.jogan.kotlinplayground.domain.model.Ticker
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class TickerMapperTest {

    private lateinit var tickerMapper: TickerMapper

    @Before
    fun setUp() {
        tickerMapper = TickerMapper()
    }

    @Test
    fun mapFromEntityMapsData() {
        val tickerEntity = TickerFactory.makeTickerEntity()
        val ticker = tickerMapper.mapFromEntity(tickerEntity)

        assertTickerDataEquality(tickerEntity, ticker)
    }

    @Test
    fun mapToEntityMapsData() {
        val cachedTicker = TickerFactory.makeTicker()
        val tickerEntity = tickerMapper.mapToEntity(cachedTicker)

        assertTickerDataEquality(tickerEntity, cachedTicker)
    }

    private fun assertTickerDataEquality(tickerEntity: TickerEntity,
                                         ticker: Ticker) {
        assertEquals(tickerEntity.id, ticker.id)
        assertEquals(tickerEntity.name, ticker.name)
        assertEquals(tickerEntity.percentChange1h, ticker.percentChange1h)
        assertEquals(tickerEntity.percentChange24h, ticker.percentChange24h)
        assertEquals(tickerEntity.percentChange7d, ticker.percentChange7d)
        assertEquals(tickerEntity.priceBtc, ticker.priceBtc)
        assertEquals(tickerEntity.priceUsd, ticker.priceUsd)
        assertEquals(tickerEntity.rank, ticker.rank)
        assertEquals(tickerEntity.symbol, ticker.symbol)
    }
}