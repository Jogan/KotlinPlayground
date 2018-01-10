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