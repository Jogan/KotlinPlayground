package com.jogan.kotlinplayground.data.source

import com.jogan.kotlinplayground.data.model.TickerEntity
import com.jogan.kotlinplayground.data.repository.TickerRemote
import com.jogan.kotlinplayground.data.test.factory.TickerFactory
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString

@RunWith(JUnit4::class)
class TickerRemoteDataStoreTest {

    private lateinit var tickerRemoteDataStore: TickerRemoteDataStore

    private lateinit var tickerRemote: TickerRemote

    @Before
    fun setUp() {
        tickerRemote = mock()
        tickerRemoteDataStore = TickerRemoteDataStore(tickerRemote)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun clearTickersThrowsException() {
        tickerRemoteDataStore.clearTickers().test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun saveTickerForCurrencyThrowsException() {
        tickerRemoteDataStore.saveTicker("bitcoin", TickerFactory.makeTickerEntityList(1)).test()
    }

    @Test
    fun getTickerForCurrencyCompletes() {
        stubTickerCacheGetTickerForCurrency(Flowable.just(TickerFactory.makeTickerEntityList(1)))
        val testObserver = tickerRemote.getTickerForCurrency("bitcoin").test()
        testObserver.assertComplete()
    }

    private fun stubTickerCacheGetTickerForCurrency(single: Flowable<List<TickerEntity>>) {
        whenever(tickerRemote.getTickerForCurrency(anyString())).thenReturn(single)
    }
}