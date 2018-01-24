package com.jogan.kotlinplayground.test.util

import com.jogan.kotlinplayground.data.model.Ticker
import com.jogan.kotlinplayground.test.util.DataFactory.randomUuid

/**
 * Factory class for Ticker related instances
 */
object TickerFactory {
    fun makeTicker(): Ticker {
        return Ticker(randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomUuid())
    }
}