package com.jogan.kotlinplayground.data.repository

import com.jogan.kotlinplayground.data.model.TickerEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Interface defining methods for the data operations related to Tickers.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface TickerDataStore {

    fun clearTickers(): Completable

    fun saveTicker(id: String, tickers: List<TickerEntity>): Completable

    fun getTickerForCurrency(id: String): Flowable<List<TickerEntity>>

    fun isCached(id: String): Single<Boolean>
}