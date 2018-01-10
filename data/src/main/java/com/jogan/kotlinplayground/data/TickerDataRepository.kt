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
package com.jogan.kotlinplayground.data

import com.jogan.kotlinplayground.data.mapper.TickerMapper
import com.jogan.kotlinplayground.data.model.TickerEntity
import com.jogan.kotlinplayground.data.source.TickerDataStoreFactory
import com.jogan.kotlinplayground.domain.model.Ticker
import com.jogan.kotlinplayground.domain.repository.TickerRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Provides an implementation of the [TickerRepository] interface for communicating to and from
 * data sources
 */
class TickerDataRepository @Inject constructor(private val factory: TickerDataStoreFactory,
                                               private val tickerMapper: TickerMapper) :
        TickerRepository {

    override fun clearTickers(): Completable {
        return factory.retrieveCacheDataStore().clearTickers()
    }

    override fun saveTickerForCurrency(id: String, tickers: List<Ticker>): Completable {
        val tickerEntities = mutableListOf<TickerEntity>()
        tickers.map { tickerEntities.add(tickerMapper.mapToEntity(it)) }
        return factory.retrieveCacheDataStore().saveTicker(id, tickerEntities)
    }

    override fun getTickerForCurrency(id: String?): Flowable<List<Ticker>> {
        if (id == null) return Flowable.empty() // TODO is this best way to handle possible null?
        return factory.retrieveCacheDataStore().isCached(id)
                .flatMapPublisher {
                    factory.retrieveDataStore(it).getTickerForCurrency(id)
                }
                .flatMap {
                    Flowable.just(it.map { tickerMapper.mapFromEntity(it) })
                }
                .flatMap {
                    saveTickerForCurrency(id, it).toSingle { it }.toFlowable()
                }
    }
}