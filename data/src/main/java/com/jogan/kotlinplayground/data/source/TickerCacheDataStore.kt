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
package com.jogan.kotlinplayground.data.source

import com.jogan.kotlinplayground.data.model.TickerEntity
import com.jogan.kotlinplayground.data.repository.TickerCache
import com.jogan.kotlinplayground.data.repository.TickerDataStore
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

open class TickerCacheDataStore @Inject constructor(private val tickerCache: TickerCache) : TickerDataStore {

    override fun clearTickers(): Completable {
        return tickerCache.clearTickers()
    }

    override fun saveTicker(id: String, tickers: List<TickerEntity>): Completable {
        return tickerCache.saveTickers(id, tickers)
                .doOnComplete {
                    tickerCache.setLastCacheTime(id, System.currentTimeMillis())
                }
    }

    override fun getTickerForCurrency(id: String): Flowable<List<TickerEntity>> {
        return tickerCache.getTickerForCurrency(id)
    }

    override fun isCached(id: String): Single<Boolean> {
        return tickerCache.isCached(id)
    }
}