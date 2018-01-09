package com.jogan.kotlinplayground.remote.mapper

import com.jogan.kotlinplayground.data.model.TickerEntity
import com.jogan.kotlinplayground.remote.model.TickerModel
import javax.inject.Inject

open class TickerEntityMapper @Inject constructor() : EntityMapper<TickerModel, TickerEntity> {
    override fun mapFromRemote(type: TickerModel): TickerEntity {
        return TickerEntity(type.id, type.name, type.symbol, type.rank, type.price_usd, type.price_btc, type.percent_change_1h, type.percent_change_24h, type.percent_change_7d)
    }
}