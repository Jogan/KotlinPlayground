package com.jogan.kotlinplayground.ui.main.browse

import com.jogan.kotlinplayground.data.ticker.ITickerRepository
import com.jogan.kotlinplayground.ui.main.browse.BrowseAction.LoadTickerAction
import com.jogan.kotlinplayground.ui.main.browse.BrowseResult.LoadTickerResult
import com.jogan.kotlinplayground.util.schedulers.BaseSchedulerProvider
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

/**
 * Contains and executes the business logic for all emitted [MviAction]
 * and returns one unique [Observable] of [MviResult].
 *
 * This could have been included inside the [MviViewModel]
 * but was separated to ease maintenance, as the [MviViewModel] was getting too big.
 */
class BrowseActionsProcessor @Inject constructor(
        private val tickerRepository: ITickerRepository,
        private val schedulerProvider: BaseSchedulerProvider) {

    private val loadTickersProcessor =
            ObservableTransformer<LoadTickerAction, BrowseResult> { actions ->
                actions.flatMap { action ->
                    tickerRepository.getTickerForCurrency("bitcoin")
                            // Transform the Single to an Observable to allow emission of multiple
                            // events down the stream (e.g. the InFlight event)
                            .toObservable()
                            // Wrap returned data into an immutable object
                            .map { ticker -> LoadTickerResult.Success(ticker) }
                            .cast(LoadTickerResult::class.java)
                            // Wrap any error into an immutable object and pass it down the stream
                            // without crashing.
                            // Because errors are data and hence, should just be part of the stream.
                            .onErrorReturn(LoadTickerResult::Failure)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            // Emit an InFlight event to notify the subscribers (e.g. the UI) we are
                            // doing work and waiting on a response.
                            // We emit it after observing on the UI thread to allow the event to be emitted
                            // on the current frame and avoid jank.
                            .startWith(BrowseResult.LoadTickerResult.InFlight)
                }
            }

    /**
     * Splits the [Observable] to match each type of [MviAction] to
     * its corresponding business logic processor. Each processor takes a defined [MviAction],
     * returns a defined [MviResult]
     * The global actionProcessor then merges all [Observable] back to
     * one unique [Observable].
     *
     *
     * The splitting is done using [Observable.publish] which allows almost anything
     * on the passed [Observable] as long as one and only one [Observable] is returned.
     *
     *
     * An security layer is also added for unhandled [MviAction] to allow early crash
     * at runtime to easy the maintenance.
     */
    internal var actionProcessor =
            ObservableTransformer<BrowseAction, BrowseResult> { actions ->
                actions.publish {
                    // Match LoadTasksAction to loadTasksProcessor
                    it.ofType(BrowseAction.LoadTickerAction::class.java)
                            .compose(loadTickersProcessor)
                            .mergeWith(
                                    // Error for not implemented actions
                                    it.filter { v -> v !is LoadTickerAction }
                                            .flatMap { w ->
                                                Observable.error<BrowseResult>(
                                                        IllegalArgumentException("Unknown Action type: $w"))
                                            }
                            )
                }
            }
}