package com.jogan.kotlinplayground.data.source

import com.jogan.kotlinplayground.data.model.TickerEntity
import com.jogan.kotlinplayground.data.repository.TickerCache
import com.jogan.kotlinplayground.data.repository.TickerDataStore
import com.jogan.kotlinplayground.data.repository.TickerRemote
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

open class TickerRemoteDataStore @Inject constructor(private val tickerRemote: TickerRemote) : TickerDataStore {

    override fun saveTicker(id: String, tickers: List<TickerEntity>): Completable {
        throw UnsupportedOperationException()
    }

    override fun clearTickers(): Completable {
        throw UnsupportedOperationException()
    }

    override fun getTickerForCurrency(id: String): Flowable<List<TickerEntity>> {
        return tickerRemote.getTickerForCurrency(id)
    }

    override fun isCached(id: String): Single<Boolean> {
        throw UnsupportedOperationException()
    }

}