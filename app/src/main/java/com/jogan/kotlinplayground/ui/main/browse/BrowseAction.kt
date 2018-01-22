package com.jogan.kotlinplayground.ui.main.browse

import com.jogan.kotlinplayground.ui.base.mvi.MviAction

sealed class BrowseAction : MviAction {
    data class LoadTickerAction(val forceUpdate: Boolean) : BrowseAction()
}