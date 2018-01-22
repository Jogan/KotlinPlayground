package com.jogan.kotlinplayground.ui.main.browse

import com.jogan.kotlinplayground.data.model.Ticker
import com.jogan.kotlinplayground.ui.base.mvi.MviViewState

sealed class BrowseViewState(
        val isLoading: Boolean = false,
        val ticker: Ticker? = null,
        val error: Throwable? = null
) : MviViewState {
    object InProgress : BrowseViewState(true, null, null)

    data class Failed(private val throwable: Throwable?) : BrowseViewState(false, null, throwable)

    data class Success(private val result: Ticker?) : BrowseViewState(false, result)

    class Idle : BrowseViewState(false, null, null)
}