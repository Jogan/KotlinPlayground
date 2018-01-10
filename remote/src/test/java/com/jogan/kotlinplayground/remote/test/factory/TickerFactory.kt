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