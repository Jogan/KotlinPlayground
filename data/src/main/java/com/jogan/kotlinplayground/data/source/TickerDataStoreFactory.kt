package com.jogan.kotlinplayground.data.source

import com.jogan.kotlinplayground.data.repository.TickerCache
import com.jogan.kotlinplayground.data.repository.TickerDataStore
import javax.inject.Inject

/**
 * Create an instance of a BufferooDataStore
 */
open class TickerDataStoreFactory @Inject constructor(
        private val tickerCache: TickerCache,
        private val tickerCacheDataStore: TickerCacheDataStore,
        private val tickerRemoteDataStore: TickerRemoteDataStore) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retrieveDataStore(isCached: Boolean): TickerDataStore {
        if (isCached && !tickerCache.isExpired()) {
            return retrieveCacheDataStore()
        }
        return retrieveRemoteDataStore()
    }

    /**
     * Return an instance of the Cache Data Store
     */
    open fun retrieveCacheDataStore(): TickerDataStore {
        return tickerCacheDataStore
    }

    /**
     * Return an instance of the Remote Data Store
     */
    open fun retrieveRemoteDataStore(): TickerDataStore {
        return tickerRemoteDataStore
    }

}