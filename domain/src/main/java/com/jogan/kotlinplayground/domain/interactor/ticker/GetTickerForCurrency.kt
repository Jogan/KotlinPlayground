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
import com.jogan.kotlinplayground.domain.interactor.FlowableUseCase
import com.jogan.kotlinplayground.domain.model.Ticker
import com.jogan.kotlinplayground.domain.repository.TickerRepository
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Use case used for retreiving a [List] of [Ticker] based on an id from the [TickerRepository]
 */
open class GetTickerForCurrency @Inject constructor(val tickerRepository: TickerRepository,
                                                    threadExecutor: ThreadExecutor,
                                                    postExecutionThread: PostExecutionThread) :
        FlowableUseCase<List<Ticker>, String?>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: String?): Flowable<List<Ticker>> {
        return tickerRepository.getTickerForCurrency(params)
    }
}