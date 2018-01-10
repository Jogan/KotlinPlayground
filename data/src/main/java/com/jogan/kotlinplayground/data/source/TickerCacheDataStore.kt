package com.jogan.kotlinplayground.data.source

import com.jogan.kotlinplayground.data.model.TickerEntity
import com.jogan.kotlinplayground.data.repository.TickerCache
import com.jogan.kotlinplayground.data.repository.TickerDataStore
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

open class TickerCacheDataStore @Inject constructor(private val tickerCache: TickerCache) : TickerDataStore {

    override fun clearTickers(): Completable {
        return tickerCache.clearTickers()
    }

    override fun saveTicker(id: String, tickers: List<TickerEntity>): Completable {
        return tickerCache.saveTickers(id, tickers)
                .doOnComplete {
                    tickerCache.setLastCacheTime(id, System.currentTimeMillis())
                }
    }

    override fun getTickerForCurrency(id: String): Flowable<List<TickerEntity>> {
        return tickerCache.getTickerForCurrency(id)
    }

    override fun isCached(id: String): Single<Boolean> {
        return tickerCache.isCached(id)
    }

}