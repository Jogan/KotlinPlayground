package com.jogan.kotlinplayground.domain.test.factory

import com.jogan.kotlinplayground.domain.model.Ticker
import com.jogan.kotlinplayground.domain.test.factory.DataFactory.Factory.randomString

/**
 * Factory class for Ticker related instances
 */
class TickerFactory {
    companion object Factory {

        fun makeTickerList(count: Int): List<Ticker> {
            val bufferoos = mutableListOf<Ticker>()
            repeat(count) {
                bufferoos.add(makeTicker())
            }
            return bufferoos
        }

        fun makeTicker(): Ticker {
            return Ticker(randomString(), randomString(), randomString(), randomString(), randomString(), randomString(), randomString(), randomString(), randomString())
        }

    }

}