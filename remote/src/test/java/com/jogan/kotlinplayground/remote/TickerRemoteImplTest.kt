package com.jogan.kotlinplayground.remote

import com.jogan.kotlinplayground.data.model.TickerEntity
import com.jogan.kotlinplayground.remote.mapper.TickerEntityMapper
import com.jogan.kotlinplayground.remote.test.factory.TickerFactory
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString

@RunWith(JUnit4::class)
class TickerRemoteImplTest {

    private lateinit var entityMapper: TickerEntityMapper
    private lateinit var coinMarketService: CoinMarketService

    private lateinit var tickerRemoteImpl: TickerRemoteImpl

    @Before
    fun setup() {
        entityMapper = mock()
        coinMarketService = mock()
        tickerRemoteImpl = TickerRemoteImpl(coinMarketService, entityMapper)
    }

    @Test
    fun getTickerForCurrencyCompletes() {
        stubCoinMarkertServiceGetTickerForCurrency(Flowable.just(TickerFactory.makeTickerResponse()))
        val testObserver = tickerRemoteImpl.getTickerForCurrency("bitcoin").test()
        testObserver.assertComplete()
    }

    @Test
    fun getTickerForCurrencyReturnsData(){
        val tickerResponse = TickerFactory.makeTickerResponse()
        stubCoinMarkertServiceGetTickerForCurrency(Flowable.just(tickerResponse))
        val tickerEntities = mutableListOf<TickerEntity>()
        tickerResponse.ticker.forEach {
            tickerEntities.add(entityMapper.mapFromRemote(it))
        }

        val testObserver = tickerRemoteImpl.getTickerForCurrency("bitcoin").test()
        testObserver.assertValue(tickerEntities)
    }

    private fun stubCoinMarkertServiceGetTickerForCurrency(observable: Flowable<CoinMarketService.TickerResponse>) {
        whenever(coinMarketService.getTickerForCurrency(anyString()))
                .thenReturn(observable)
    }
}