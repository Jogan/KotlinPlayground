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
package com.jogan.kotlinplayground.remote

import com.jogan.kotlinplayground.data.model.TickerEntity
import com.jogan.kotlinplayground.data.repository.TickerRemote
import com.jogan.kotlinplayground.remote.mapper.TickerEntityMapper
import io.reactivex.Flowable
import javax.inject.Inject

class TickerRemoteImpl @Inject constructor(private val coinMarketService: CoinMarketService,
                                           private val entityMapper: TickerEntityMapper) : TickerRemote {
    override fun getTickerForCurrency(id: String): Flowable<List<TickerEntity>> {
        return coinMarketService.getTickerForCurrency(id)
                .map { it.ticker }
                .map {
                    val entities = mutableListOf<TickerEntity>()
                    it.forEach { entities.add(entityMapper.mapFromRemote(it)) }
                    entities
                }
    }
}