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