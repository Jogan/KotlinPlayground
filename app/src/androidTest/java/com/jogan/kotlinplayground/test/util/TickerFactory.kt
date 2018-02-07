package com.jogan.kotlinplayground.test.util

import com.jogan.kotlinplayground.data.ticker.Ticker
import com.jogan.kotlinplayground.test.util.DataFactory.randomLong
import com.jogan.kotlinplayground.test.util.DataFactory.randomUuid

/**
 * Factory class for Ticker related instances
 */
object TickerFactory {
    fun makeTicker(): Ticker {
        return Ticker(randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomLong().toString(), randomLong().toString(), randomLong().toString())
    }

    fun makeTickerWithName(name: String): Ticker {
        return Ticker(randomUuid(), name, randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomLong().toString(), randomLong().toString(), randomLong().toString())
    }

    fun makeTickerList(count: Int): List<Ticker> {
        val tickers = mutableListOf<Ticker>()
        repeat(count) {
            tickers.add(TickerFactory.makeTicker())
        }
        return tickers
    }
}