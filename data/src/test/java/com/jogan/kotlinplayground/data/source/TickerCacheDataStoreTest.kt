package com.jogan.kotlinplayground.data.source

import com.jogan.kotlinplayground.data.model.TickerEntity
import com.jogan.kotlinplayground.data.repository.TickerCache
import com.jogan.kotlinplayground.data.test.factory.TickerFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString

@RunWith(JUnit4::class)
class TickerCacheDataStoreTest {
    private lateinit var tickerCacheDataStore: TickerCacheDataStore

    private lateinit var tickerCache: TickerCache

    @Before
    fun setUp() {
        tickerCache = mock()
        tickerCacheDataStore = TickerCacheDataStore(tickerCache)
    }

    @Test
    fun clearTickersCompletes() {
        stubTickerCacheClearTickers(Completable.complete())
        val testObserver = tickerCacheDataStore.clearTickers().test()
        testObserver.assertComplete()
    }

    @Test
    fun saveTickerForCurrencyCompletes() {
        stubTickerCacheSaveTicker(Completable.complete())
        val testObserver = tickerCacheDataStore.saveTicker("bitcoin", TickerFactory.makeTickerEntityList(1)).test()
        testObserver.assertComplete()
    }

    @Test
    fun getTickerForCurrencyCompletes() {
        stubTickerCacheGetTickerForCurrency(Flowable.just(TickerFactory.makeTickerEntityList(1)))
        val testObserver = tickerCacheDataStore.getTickerForCurrency(anyString()).test()
        testObserver.assertComplete()
    }

    private fun stubTickerCacheSaveTicker(completable: Completable) {
        whenever(tickerCache.saveTickers(anyString(), any()))
                .thenReturn(completable)
    }

    private fun stubTickerCacheGetTickerForCurrency(single: Flowable<List<TickerEntity>>) {
        whenever(tickerCache.getTickerForCurrency(anyString()))
                .thenReturn(single)
    }

    private fun stubTickerCacheClearTickers(completable: Completable) {
        whenever(tickerCache.clearTickers())
                .thenReturn(completable)
    }
}
