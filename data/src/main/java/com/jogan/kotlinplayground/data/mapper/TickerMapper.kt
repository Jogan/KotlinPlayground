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