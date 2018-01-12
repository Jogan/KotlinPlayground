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
package com.jogan.kotlinplayground.api.mapper

import com.jogan.kotlinplayground.api.models.TickerModel
import com.jogan.kotlinplayground.data.model.Ticker
import com.jogan.kotlinplayground.remote.mapper.EntityMapper
import javax.inject.Inject

open class TickerMapper @Inject constructor() : EntityMapper<TickerModel, Ticker> {
    override fun mapFromRemote(type: TickerModel): Ticker {
        return Ticker(type.id, type.name, type.symbol, type.rank, type.price_usd, type.price_btc, type.percent_change_1h, type.percent_change_24h, type.percent_change_7d)
    }
}