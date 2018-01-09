package com.jogan.kotlinplayground.domain.interactor.ticker

import com.jogan.kotlinplayground.domain.executor.PostExecutionThread
import com.jogan.kotlinplayground.domain.executor.ThreadExecutor
import com.jogan.kotlinplayground.domain.model.Ticker
import com.jogan.kotlinplayground.domain.repository.TickerRepository
import com.jogan.kotlinplayground.domain.test.factory.TickerFactory
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class GetTickerForCurrencyTest {

    private lateinit var getTickerForCurrency: GetTickerForCurrency

    private lateinit var mockThreadExecutor: ThreadExecutor
    private lateinit var mockPostExecutionThread: PostExecutionThread
    private lateinit var mockTickerRepository: TickerRepository

    @Before
    fun setUp() {
        mockThreadExecutor = mock()
        mockPostExecutionThread = mock()
        mockTickerRepository = mock()
        getTickerForCurrency = GetTickerForCurrency(mockTickerRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
        val params = "bitcoin"
        getTickerForCurrency.buildUseCaseObservable(params)
        verify(mockTickerRepository).getTickerForCurrency(params)
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        stubTickerRepositoryGetTickerForCurrency(Flowable.just(TickerFactory.makeTickerList(1)))
        val testObserver = getTickerForCurrency.buildUseCaseObservable("bitcoin").test()
        testObserver.assertComplete()
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val tickers = TickerFactory.makeTickerList(1)
        stubTickerRepositoryGetTickerForCurrency(Flowable.just(tickers))
        val testObserver = getTickerForCurrency.buildUseCaseObservable("bitcoin").test()
        testObserver.assertValue(tickers)
    }

    private fun stubTickerRepositoryGetTickerForCurrency(single: Flowable<List<Ticker>>) {
        whenever(mockTickerRepository.getTickerForCurrency(anyString()))
                .thenReturn(single)
    }
}