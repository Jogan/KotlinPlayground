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
package com.jogan.kotlinplayground.data.ticker

import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

open class TickerRepository @Inject constructor(
        @Named("remote") private val tickerRemoteDataSource: TickerDataSource,
        @Named("local") private val tickerLocalDataSource: TickerDataSource
) : ITickerRepository {

    override fun getTickerForCurrency(id: String): Single<Ticker> {
        // TODO cache logic
        return tickerRemoteDataSource.getTickerForCurrency(id)
                .doOnError { Timber.e(it, "error in service") }
                .map { it.first() }
    }

    override fun getTickers(start: Int, limit: Int): Single<List<Ticker>> {
        // TODO cache logic
        return tickerRemoteDataSource.getTickers(start, limit)
                .doOnError { Timber.e(it, "error in service") }
    }

    override fun getAndCacheTickers(): Single<Boolean> {
        return tickerLocalDataSource
                .hasTickers()
                .doOnSuccess { Timber.d("hasTickers = %s", it) }
                .flatMap { it ->
                    if (it) Single.just(true)
                    else tickerRemoteDataSource
                            .getTickers(0, 0)
                            .flatMapCompletable { tickers -> tickerLocalDataSource.saveTickers(tickers) }
                            .andThen(Single.just(true))
                }
    }
}
