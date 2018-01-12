package com.jogan.kotlinplayground.data.ticker

import com.jogan.kotlinplayground.data.model.Ticker
import io.reactivex.Single
import javax.inject.Inject

open class TickerRepository @Inject constructor(
        private val tickerRemoteDataSource: TickerDataSource,
        private val tickerLocalDataSource: TickerDataSource
) : ITickerRepository {

    override fun getTickerForCurrency(id: String): Single<Ticker> {
        // TODO cache logic
        return tickerRemoteDataSource.getTickerForCurrency(id)
                .map { it.first() }
    }
}
