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
package com.jogan.kotlinplayground.data.test.factory

import com.jogan.kotlinplayground.data.model.TickerEntity
import com.jogan.kotlinplayground.data.test.factory.DataFactory.Factory.randomString
import com.jogan.kotlinplayground.domain.model.Ticker

/**
 * Factory class for Bufferoo related instances
 */
class TickerFactory {

    companion object Factory {

        fun makeTickerEntity(): TickerEntity {
            return TickerEntity(randomString(), randomString(), randomString(), randomString(), randomString(), randomString(), randomString(), randomString(), randomString())
        }

        fun makeTicker(): Ticker {
            return Ticker(randomString(), randomString(), randomString(), randomString(), randomString(), randomString(), randomString(), randomString(), randomString())
        }

        fun makeTickerEntityList(count: Int): List<TickerEntity> {
            val tickerEntities = mutableListOf<TickerEntity>()
            repeat(count) {
                tickerEntities.add(makeTickerEntity())
            }
            return tickerEntities
        }

        fun makeTickerList(count: Int): List<Ticker> {
            val bufferoos = mutableListOf<Ticker>()
            repeat(count) {
                bufferoos.add(makeTicker())
            }
            return bufferoos
        }
    }
}