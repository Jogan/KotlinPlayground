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