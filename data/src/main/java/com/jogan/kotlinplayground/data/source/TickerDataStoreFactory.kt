/*
 * Copyright 2017 John Hogan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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