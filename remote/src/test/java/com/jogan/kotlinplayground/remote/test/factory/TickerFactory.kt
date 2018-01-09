package com.jogan.kotlinplayground.remote.test.factory

import com.jogan.kotlinplayground.remote.CoinMarketService
import com.jogan.kotlinplayground.remote.model.TickerModel
import com.jogan.kotlinplayground.remote.test.factory.DataFactory.Factory.randomString

/**
 * Factory class for Ticker related instances
 */
class TickerFactory {
    companion object Factory {

        fun makeTickerResponse(): CoinMarketService.TickerResponse {
            val tickerResponse = CoinMarketService.TickerResponse()
            tickerResponse.ticker = makeTickerModelList(1)
            return tickerResponse
        }

        fun makeTickerModelList(count: Int): List<TickerModel> {
            val tickerEntities = mutableListOf<TickerModel>()
            repeat(count) {
                tickerEntities.add(makeTickerModel())
            }
            return tickerEntities
        }

        fun makeTickerModel(): TickerModel {
            return TickerModel(randomString(), randomString(), randomString(), randomString(), randomString(), randomString(), randomString(), randomString(), randomString())
        }
    }
}