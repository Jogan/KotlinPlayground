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

import com.jogan.kotlinplayground.ui.base.mvi.MviIntent
import com.jogan.kotlinplayground.ui.base.mvi.MviViewModel
import com.jogan.kotlinplayground.util.RxAwareViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

open class HomeViewModel @Inject internal constructor(
        private val processor: HomeActionsProcessor
) : RxAwareViewModel(), MviViewModel<HomeIntent, HomeViewState> {

    private var intentsSubject: PublishSubject<HomeIntent> = PublishSubject.create()

    /**
     * Take only the first ever InitialIntent and all intents of other types
     * to avoid reloading data on config changes
     */
    private val intentFilter: ObservableTransformer<HomeIntent, HomeIntent> =
            ObservableTransformer<HomeIntent, HomeIntent> {
                it.publish {
                    Observable.merge(it.ofType(HomeIntent.InitialIntent::class.java).take(1),
                            it.filter({ intent -> intent !is HomeIntent.InitialIntent }))
                }
            }

    private val reducer: BiFunction<HomeViewState, HomeResult, HomeViewState> =
            BiFunction<HomeViewState, HomeResult, HomeViewState> { previousState, result ->
                when (result) {
                    is HomeResult.SyncTickerResult -> when (result) {
                        is HomeResult.SyncTickerResult.Success -> HomeViewState.Success()
                        is HomeResult.SyncTickerResult.Failure -> HomeViewState.Failed(result.error)
                        is HomeResult.SyncTickerResult.InFlight -> HomeViewState.InProgress
                    }
                }
            }

    private val statesSubject: Observable<HomeViewState> = compose()

    override fun processIntents(intents: Observable<HomeIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): Observable<HomeViewState> {
        return statesSubject
    }

    /**
     * Compose all components to create the stream logic
     */
    private fun compose(): Observable<HomeViewState> {
        return intentsSubject
                .compose(intentFilter)
                .map(this::actionFromIntent)
                .compose(processor.actionProcessor)
                // Cache each state and pass it to the reducer to create a new state from
                // the previous cached one and the latest Result emitted from the action processor.
                // The Scan operator is used here for the caching.
                .scan<HomeViewState>(HomeViewState.Idle(), reducer)
                // Emit the last one event of the stream on subscription
                // Useful when a View rebinds to the ViewModel after rotation.
                .replay(1)
                // Create the stream on creation without waiting for anyone to subscribe
                // This allows the stream to stay alive even when the UI disconnects and
                // match the stream's lifecycle to the ViewModel's one.
                .autoConnect(0)
    }

    private fun actionFromIntent(intent: MviIntent): HomeAction {
        return when (intent) {
            is HomeIntent.InitialIntent -> HomeAction.SyncTickersAction
            else -> throw UnsupportedOperationException("Oops, that looks like an unknown intent: " + intent)
        }
    }
}