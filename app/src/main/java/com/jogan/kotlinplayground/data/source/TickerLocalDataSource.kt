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

import com.jogan.kotlinplayground.data.db.TickerDao
import com.jogan.kotlinplayground.data.model.Ticker
import io.reactivex.Completable
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class TickerLocalDataSource @Inject constructor(
        private val tickerDao: TickerDao)
    : TickerDataSource {

    override fun hasTickers(): Single<Boolean> {
        return tickerDao.tickerCount()
                .doOnSuccess { Timber.d("## tickerCount = %s", it) }
                .map { it > 0 }
    }

    override fun saveTickers(tickers: List<Ticker>): Completable {
        return Completable.fromCallable {
            tickers.forEach { it ->
                run {
                    Timber.d("inserting Ticker -> %s", it.toString())
                    tickerDao.insertTicker(it)
                }
            }
        }
    }

    override fun getTickers(start: Int, limit: Int): Single<List<Ticker>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTickerForCurrency(id: String): Single<List<Ticker>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}