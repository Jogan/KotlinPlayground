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
package com.jogan.kotlinplayground.data.mapper

import com.jogan.kotlinplayground.data.model.TickerEntity
import com.jogan.kotlinplayground.domain.model.Ticker
import javax.inject.Inject

open class TickerMapper @Inject constructor() : Mapper<TickerEntity, Ticker> {
    /**
     * Map a [TickerEntity] instance to a [Ticker] instance
     */
    override fun mapFromEntity(type: TickerEntity): Ticker {
        return Ticker(type.id, type.name, type.symbol, type.rank, type.priceUsd, type.priceBtc, type.percentChange1h, type.percentChange24h, type.percentChange7d)
    }

    /**
     * Map a [Ticker] instance to a [TickerEntity] instance
     */
    override fun mapToEntity(type: Ticker): TickerEntity {
        return TickerEntity(type.id, type.name, type.symbol, type.rank, type.priceUsd, type.priceBtc, type.percentChange1h, type.percentChange24h, type.percentChange7d)
    }
}