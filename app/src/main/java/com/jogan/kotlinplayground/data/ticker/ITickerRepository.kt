package com.jogan.kotlinplayground.data.ticker

import com.jogan.kotlinplayground.data.model.Ticker
import io.reactivex.Single

interface ITickerRepository {
    fun getTickerForCurrency(id: String): Single<Ticker>
}