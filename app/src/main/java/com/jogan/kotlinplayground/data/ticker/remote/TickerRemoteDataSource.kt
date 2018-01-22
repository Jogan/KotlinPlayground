package com.jogan.kotlinplayground.data.ticker.remote

import com.jogan.kotlinplayground.api.CoinMarketService
import com.jogan.kotlinplayground.api.mapper.TickerMapper
import com.jogan.kotlinplayground.data.model.Ticker
import com.jogan.kotlinplayground.data.ticker.TickerDataSource
import io.reactivex.Single
import javax.inject.Inject

class TickerRemoteDataSource @Inject constructor(
        private val coinMarketService: CoinMarketService,
        private val tickerMapper: TickerMapper) : TickerDataSource {

    override fun getTickerForCurrency(id: String): Single<List<Ticker>> {
        return coinMarketService.getTickerForCurrency(id)
                .flatMap {
                    Single.just(it.map { tickerMapper.mapFromRemote(it) })
                }
    }
}