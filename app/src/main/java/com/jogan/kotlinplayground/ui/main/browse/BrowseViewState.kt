package com.jogan.kotlinplayground.ui.main.browse

import com.jogan.kotlinplayground.data.model.Ticker
import com.jogan.kotlinplayground.ui.base.mvi.MviViewState

data class BrowseViewState(
        val isLoading: Boolean,
        val ticker: Ticker?,
        val error: Throwable?
) : MviViewState {
    companion object {
        fun idle(): BrowseViewState {
            return BrowseViewState(
                    isLoading = false,
                    ticker = null,
                    error = null
            )
        }
    }
}