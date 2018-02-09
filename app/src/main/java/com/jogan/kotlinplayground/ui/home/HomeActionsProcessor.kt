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
package com.jogan.kotlinplayground.ui.home

import com.jogan.kotlinplayground.data.ITickerRepository
import com.jogan.kotlinplayground.ui.home.HomeAction.SyncTickersAction
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
class HomeActionsProcessor @Inject constructor(
        private val tickerRepository: ITickerRepository,
        private val schedulerProvider: BaseSchedulerProvider) {

    private val syncTickersProcessor =
            ObservableTransformer<SyncTickersAction, HomeResult> { actions ->
                actions.flatMap { action ->
                    tickerRepository.getAndCacheTickers()
                            // Transform the Single to an Observable to allow emission of multiple
                            // events down the stream (e.g. the InFlight event)
                            .toObservable()
                            // Wrap returned data into an immutable object
                            .map { result -> HomeResult.SyncTickerResult.Success(result) }
                            .cast(HomeResult.SyncTickerResult::class.java)
                            // Wrap any error into an immutable object and pass it down the stream
                            // without crashing.
                            // Because errors are data and hence, should just be part of the stream.
                            .onErrorReturn(HomeResult.SyncTickerResult::Failure)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            // Emit an InFlight event to notify the subscribers (e.g. the UI) we are
                            // doing work and waiting on a response.
                            // We emit it after observing on the UI thread to allow the event to be emitted
                            // on the current frame and avoid jank.
                            .startWith(HomeResult.SyncTickerResult.InFlight)
                }
            }

    internal var actionProcessor =
            ObservableTransformer<HomeAction, HomeResult> { actions ->
                actions.publish {
                    it.ofType(SyncTickersAction::class.java)
                            .compose(syncTickersProcessor)
                            .mergeWith(
                                    // Error for not implemented actions
                                    it.filter { v -> v !is SyncTickersAction }
                                            .flatMap { w ->
                                                Observable.error<HomeResult>(
                                                        IllegalArgumentException("Unknown Action type: $w"))
                                            }
                            )
                }
            }
}