package com.jogan.kotlinplayground.data.ticker.local

import com.jogan.kotlinplayground.data.model.Ticker
import com.jogan.kotlinplayground.data.ticker.TickerDataSource
import io.reactivex.Single

class TickerLocalDataSource : TickerDataSource {
    override fun getTickerForCurrency(id: String): Single<List<Ticker>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}