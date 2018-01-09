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