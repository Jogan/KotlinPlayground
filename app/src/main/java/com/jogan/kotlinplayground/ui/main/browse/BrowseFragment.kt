package com.jogan.kotlinplayground.ui.main.browse

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jogan.kotlinplayground.R
import com.jogan.kotlinplayground.ui.base.BaseFragment
import com.jogan.kotlinplayground.ui.base.mvi.MviView
import io.reactivex.Observable
import javax.inject.Inject

class BrowseFragment : BaseFragment(), MviView<BrowseIntent, BrowseViewState> {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    internal lateinit var viewModel: BrowseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BrowseViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_browse, container, false)
    }

    override fun intents(): Observable<BrowseIntent> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun render(state: BrowseViewState) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}