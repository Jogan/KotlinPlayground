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
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString

@RunWith(JUnit4::class)
class TickerDataStoreFactoryTest {

    private lateinit var tickerDataStoreFactory: TickerDataStoreFactory

    private lateinit var tickerCache: TickerCache
    private lateinit var tickerCacheDataStore: TickerCacheDataStore
    private lateinit var tickerRemoteDataStore: TickerRemoteDataStore

    @Before
    fun setUp() {
        tickerCache = mock()
        tickerCacheDataStore = mock()
        tickerRemoteDataStore = mock()
        tickerDataStoreFactory = TickerDataStoreFactory(tickerCache, tickerCacheDataStore, tickerRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreWhenNotCachedReturnsRemoteDataStore() {
        stubTickerCacheIsCached(Single.just(false))
        val tickerDataStore = tickerDataStoreFactory.retrieveDataStore(false)
        assert(tickerDataStore is TickerRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreWhenCacheExpiredReturnsRemoteDataStore() {
        stubTickerCacheIsCached(Single.just(true))
        stubTickerCacheIsExpired(true)
        val tickerDataStore = tickerDataStoreFactory.retrieveDataStore(true)
        assert(tickerDataStore is TickerRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreReturnsCacheDataStore() {
        stubTickerCacheIsCached(Single.just(true))
        stubTickerCacheIsExpired(false)
        val tickerDataStore = tickerDataStoreFactory.retrieveDataStore(true)
        assert(tickerDataStore is TickerCacheDataStore)
    }

    @Test
    fun retrieveRemoteDataStoreReturnsRemoteDataStore() {
        val tickerDataStore = tickerDataStoreFactory.retrieveRemoteDataStore()
        assert(tickerDataStore is TickerRemoteDataStore)
    }

    @Test
    fun retrieveCacheDataStoreReturnsCacheDataStore() {
        val tickerDataStore = tickerDataStoreFactory.retrieveCacheDataStore()
        assert(tickerDataStore is TickerCacheDataStore)
    }

    private fun stubTickerCacheIsCached(single: Single<Boolean>) {
        whenever(tickerCache.isCached(anyString()))
                .thenReturn(single)
    }

    private fun stubTickerCacheIsExpired(isExpired: Boolean) {
        whenever(tickerCache.isExpired())
                .thenReturn(isExpired)
    }
}