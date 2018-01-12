package com.jogan.kotlinplayground.data.ticker

import com.jogan.kotlinplayground.data.model.Ticker
import io.reactivex.Single

interface TickerDataSource {
    fun getTickerForCurrency(id: String) : Single<List<Ticker>>
}