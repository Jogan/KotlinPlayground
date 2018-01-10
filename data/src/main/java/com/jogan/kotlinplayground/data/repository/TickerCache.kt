package com.jogan.kotlinplayground.data.repository

import com.jogan.kotlinplayground.data.model.TickerEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Interface defining methods for the caching of Tickers. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface TickerCache {

    /**
     * Clear all Tickers from the cache
     */
    fun clearTickers(): Completable

    /**
     * Save a given list of Tickers to the cache
     */
    fun saveTickers(id: String, tickers: List<TickerEntity>): Completable

    /**
     * Retrieve a list of Tickers for a given id, from the cache
     */
    fun getTickerForCurrency(id: String): Flowable<List<TickerEntity>>

    /**
     * Check whether there is a list of tickers stored in the cache.
     *
     * @return true if the list is cached, otherwise false
     */
    fun isCached(id: String): Single<Boolean>

    /**
     * Set a point in time at when the cache was last updated for a specified ID
     *
     * @param lastCache the point in time at when the cache was last updated
     */
    fun setLastCacheTime(id: String, lastCache: Long)

    /**
     * Check if the cache is expired.
     *
     * @return true if the cache is expired, otherwise false
     */
    fun isExpired(): Boolean
}