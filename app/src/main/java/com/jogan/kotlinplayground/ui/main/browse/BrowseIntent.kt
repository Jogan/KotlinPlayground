package com.jogan.kotlinplayground.ui.main.browse

import com.jogan.kotlinplayground.ui.base.mvi.MviIntent

sealed class BrowseIntent : MviIntent {
    object InitialIntent : BrowseIntent()
}