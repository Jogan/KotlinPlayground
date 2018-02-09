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
package com.jogan.kotlinplayground.test.util

import com.jogan.kotlinplayground.data.model.Ticker
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