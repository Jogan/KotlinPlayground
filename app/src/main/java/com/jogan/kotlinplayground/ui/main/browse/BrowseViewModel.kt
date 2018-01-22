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
package com.jogan.kotlinplayground.ui.main.browse

import com.jogan.kotlinplayground.ui.base.mvi.MviIntent
import com.jogan.kotlinplayground.ui.base.mvi.MviViewModel
import com.jogan.kotlinplayground.ui.main.browse.BrowseResult.LoadTickerResult
import com.jogan.kotlinplayground.util.RxAwareViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Listens to user actions from the UI ([BrowseFragment]), retrieves the data and updates the
 * UI as required.
 *
 * @property processor Contains and executes the business logic of all emitted
 * actions.
 */
open class BrowseViewModel @Inject internal constructor(
        private val processor: BrowseActionsProcessor
) : RxAwareViewModel(), MviViewModel<BrowseIntent, BrowseViewState> {

    /**
     * Proxy subject used to keep the stream alive even after the UI gets recycled.
     * This is basically used to keep ongoing events and the last cached State alive
     * while the UI disconnects and reconnects on config changes.
     */
    private var intentsSubject: PublishSubject<BrowseIntent> = PublishSubject.create()
    /**
     * Take only the first ever InitialIntent and all intents of other types
     * to avoid reloading data on config changes
     */
    private val intentFilter: ObservableTransformer<BrowseIntent, BrowseIntent> =
            ObservableTransformer<BrowseIntent, BrowseIntent> {
                it.publish {
                    Observable.merge(it.ofType(BrowseIntent.InitialIntent::class.java).take(1),
                            it.filter({ intent -> intent !is BrowseIntent.InitialIntent }))
                }
            }

    private val reducer: BiFunction<BrowseViewState, BrowseResult, BrowseViewState> =
            BiFunction<BrowseViewState, BrowseResult, BrowseViewState> { previousState, result ->
                when (result) {
                    is LoadTickerResult -> when (result) {
                        is BrowseResult.LoadTickerResult.Success -> BrowseViewState.Success(result = result.tickers)
                        is BrowseResult.LoadTickerResult.Failure -> BrowseViewState.Failed(result.error)
                        is BrowseResult.LoadTickerResult.InFlight -> BrowseViewState.InProgress
                    }
                }
            }
    private val statesSubject: Observable<BrowseViewState> = compose()

    override fun processIntents(intents: Observable<BrowseIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): Observable<BrowseViewState> {
        return statesSubject
    }

    /**
     * Compose all components to create the stream logic
     */
    private fun compose(): Observable<BrowseViewState> {
        return intentsSubject
                .compose(intentFilter)
                .map(this::actionFromIntent)
                .compose(processor.actionProcessor)
                // Cache each state and pass it to the reducer to create a new state from
                // the previous cached one and the latest Result emitted from the action processor.
                // The Scan operator is used here for the caching.
                .scan<BrowseViewState>(BrowseViewState.Idle(), reducer)
                // Emit the last one event of the stream on subscription
                // Useful when a View rebinds to the ViewModel after rotation.
                .replay(1)
                // Create the stream on creation without waiting for anyone to subscribe
                // This allows the stream to stay alive even when the UI disconnects and
                // match the stream's lifecycle to the ViewModel's one.
                .autoConnect(0)
    }

    private fun actionFromIntent(intent: MviIntent): BrowseAction {
        return when (intent) {
            is BrowseIntent.InitialIntent -> BrowseAction.LoadTickerAction(true)
            else -> throw UnsupportedOperationException(
                    "Oops, that looks like an unknown intent: " + intent)
        }
    }
}