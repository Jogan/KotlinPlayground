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
package com.jogan.kotlinplayground.data.ticker.remote

import com.jogan.kotlinplayground.api.CoinMarketService
import com.jogan.kotlinplayground.api.mapper.TickerMapper
import com.jogan.kotlinplayground.data.ticker.Ticker
import com.jogan.kotlinplayground.data.ticker.TickerDataSource
import io.reactivex.Single
import javax.inject.Inject

class TickerRemoteDataSource @Inject constructor(
        private val coinMarketService: CoinMarketService,
        private val tickerMapper: TickerMapper) : TickerDataSource {

    override fun getTickers(start: Int, limit: Int): Single<List<Ticker>> {
        return coinMarketService.getTickers(start, limit)
                .flatMap {
                    Single.just(it.map { tickerMapper.mapFromRemote(it) })
                }
    }

    override fun getTickerForCurrency(id: String): Single<List<Ticker>> {
        return coinMarketService.getTickerForCurrency(id)
                .flatMap {
                    Single.just(it.map { tickerMapper.mapFromRemote(it) })
                }
    }
}