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