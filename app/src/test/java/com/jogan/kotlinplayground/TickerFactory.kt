package com.jogan.kotlinplayground

import com.jogan.kotlinplayground.DataFactory.Factory.randomUuid
import com.jogan.kotlinplayground.data.ticker.Ticker

/**
 * Factory class for Bufferoo related instances
 */
class TickerFactory {

    companion object Factory {

        fun makeTickerList(count: Int): List<Ticker> {
            val tickers = mutableListOf<Ticker>()
            repeat(count) {
                tickers.add(makeTicker())
            }
            return tickers
        }

        fun makeTicker(): Ticker {
            return Ticker(randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomUuid())
        }
    }
}