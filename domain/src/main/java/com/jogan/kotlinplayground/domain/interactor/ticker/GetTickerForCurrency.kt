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