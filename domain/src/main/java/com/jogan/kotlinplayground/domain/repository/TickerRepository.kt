package com.jogan.kotlinplayground.domain.repository

import com.jogan.kotlinplayground.domain.model.Ticker
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Interface defining methods for how the data layer can pass data to and from the Domain layer.
 * This is to be implemented by the data layer, setting the requirements for the
 * operations that need to be implemented
 */
interface TickerRepository {
    fun clearTickers(): Completable

    fun saveTickers(tickers: List<Ticker>): Completable

    fun getTickerForCurrency(id: String?): Flowable<List<Ticker>>
}