package com.jogan.kotlinplayground.ui.main.browse

import com.jogan.kotlinplayground.TickerFactory
import com.jogan.kotlinplayground.data.ticker.Ticker
import com.jogan.kotlinplayground.data.ticker.TickerRepository
import com.jogan.kotlinplayground.util.schedulers.BaseSchedulerProvider
import com.jogan.kotlinplayground.util.schedulers.ImmediateSchedulerProvider
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock

@RunWith(JUnit4::class)
class BrowseViewModelTest {

    @Mock
    private lateinit var tickerRepository: TickerRepository

    private lateinit var schedulerProvider: BaseSchedulerProvider
    private lateinit var browseViewModel: BrowseViewModel
    private lateinit var browseActionsProcessor: BrowseActionsProcessor

    @Before
    fun setUp() {
        tickerRepository = mock()
        schedulerProvider = ImmediateSchedulerProvider
        browseActionsProcessor = BrowseActionsProcessor(tickerRepository, schedulerProvider)
        browseViewModel = BrowseViewModel(browseActionsProcessor)
    }

    @Test
    fun initialIntentReturnsSuccess() {
        val tickers = TickerFactory.makeTickerList(3)
        stubGetTickerForCurrency(Single.just(tickers))

        val testObserver = browseViewModel.states().test()

        browseViewModel.processIntents(Observable.just(BrowseIntent.InitialIntent))

        testObserver.assertValueAt(2, { it is BrowseViewState.Success })
    }

    @Test
    fun initialIntentWhenSuccessIsNotLoading() {
        val tickers = TickerFactory.makeTickerList(3)
        stubGetTickerForCurrency(Single.just(tickers))

        val testObserver = browseViewModel.states().test()

        browseViewModel.processIntents(Observable.just(BrowseIntent.InitialIntent))

        testObserver.assertValueAt(2, { !it.isLoading })
    }

    @Test
    fun initialIntentReturnsData() {
        val tickers = TickerFactory.makeTickerList(3)
        stubGetTickerForCurrency(Single.just(tickers))

        val testObserver = browseViewModel.states().test()

        browseViewModel.processIntents(Observable.just(BrowseIntent.InitialIntent))

        testObserver.assertValueAt(2, { it.tickers == tickers })
    }

    @Test
    fun initialIntentReturnsError() {
        stubGetTickerForCurrency(Single.error(RuntimeException()))

        val testObserver = browseViewModel.states().test()

        browseViewModel.processIntents(Observable.just(BrowseIntent.InitialIntent))

        testObserver.assertValueAt(2, { it is BrowseViewState.Failed })
    }

    @Test
    fun initialIntentWhenErrorIsNotLoading() {
        stubGetTickerForCurrency(Single.error(RuntimeException()))

        val testObserver = browseViewModel.states().test()

        browseViewModel.processIntents(Observable.just(BrowseIntent.InitialIntent))

        testObserver.assertValueAt(2, { !it.isLoading })
    }

    @Test
    fun initialIntentWhenErrorHasNoData() {
        stubGetTickerForCurrency(Single.error(RuntimeException()))

        val testObserver = browseViewModel.states().test()

        browseViewModel.processIntents(Observable.just(BrowseIntent.InitialIntent))

        testObserver.assertValueAt(2, { it.tickers == null })
    }

    @Test
    fun initialIntentReturnsLoading() {
        val tickers = TickerFactory.makeTickerList(3)
        stubGetTickerForCurrency(Single.just(tickers))

        val testObserver = browseViewModel.states().test()

        browseViewModel.processIntents(Observable.just(BrowseIntent.InitialIntent))

        testObserver.assertValueAt(1, { it is BrowseViewState.InProgress })
    }

    @Test
    fun initialIntentBeginsAsIdle() {
        val tickers = TickerFactory.makeTickerList(3)
        stubGetTickerForCurrency(Single.just(tickers))

        val testObserver = browseViewModel.states().test()

        browseViewModel.processIntents(Observable.just(BrowseIntent.InitialIntent))

        testObserver.assertValueAt(0, { it is BrowseViewState.Idle })
    }

    // NOTE: Is it better to separate the above tests out individual or have one test combining the flows like below?

    @Test
    fun initialIntentEmitsCorrectViewStates() {
        val tickers = TickerFactory.makeTickerList(3)
        stubGetTickerForCurrency(Single.just(tickers))

        val testObserver = browseViewModel.states().test()

        browseViewModel.processIntents(Observable.just(BrowseIntent.InitialIntent))

        testObserver.assertValueAt(0, { it is BrowseViewState.Idle })
        testObserver.assertValueAt(1, { it is BrowseViewState.InProgress })
        testObserver.assertValueAt(2, { it is BrowseViewState.Success })
        testObserver.assertNoErrors()
    }

    private fun stubGetTickerForCurrency(single: Single<List<Ticker>>) {
        whenever(tickerRepository.getTickers(anyInt(), anyInt()))
                .thenReturn(single)
    }
}


