package com.jogan.kotlinplayground.data.repository

import com.jogan.kotlinplayground.data.model.TickerEntity
import io.reactivex.Flowable

/**
 * Interface defining methods for the retrieving of Tickers from the network. This is to be implemented by the
 * remote layer, using this interface as a way of communicating.
 */
interface TickerRemote {

    fun getTickerForCurrency(id: String): Flowable<List<TickerEntity>>
}