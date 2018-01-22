package com.jogan.kotlinplayground.ui.main.browse

import com.jogan.kotlinplayground.data.model.Ticker
import com.jogan.kotlinplayground.ui.base.mvi.MviResult

sealed class BrowseResult : MviResult {
    sealed class LoadTickerResult : BrowseResult() {
        data class Success(val tickers: Ticker) : LoadTickerResult()
        data class Failure(val error: Throwable) : LoadTickerResult()
        object InFlight : LoadTickerResult()
    }
}