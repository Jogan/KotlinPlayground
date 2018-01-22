package com.jogan.kotlinplayground.ui.main.browse

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jogan.kotlinplayground.R
import com.jogan.kotlinplayground.data.model.Ticker
import com.jogan.kotlinplayground.ui.base.BaseFragment
import com.jogan.kotlinplayground.ui.base.mvi.MviView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_browse.*
import timber.log.Timber
import javax.inject.Inject

class BrowseFragment : BaseFragment(), MviView<BrowseIntent, BrowseViewState> {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: BrowseViewModel

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(BrowseViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_browse, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind()
    }

    /**
     * Connect the [MviView] with the [MviViewModel]
     * We subscribe to the [MviViewModel] before passing it the [MviView]'s [MviIntent]s.
     * If we were to pass [MviIntent]s to the [MviViewModel] before listening to it,
     * emitted [MviViewState]s could be lost
     */
    private fun bind() {
        // Subscribe to the ViewModel and call render for every emitted state
        disposables.add(viewModel.states().subscribe({ render(it) }))
        // Pass the UI's intents to the ViewModel
        viewModel.processIntents(intents())
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

    override fun intents(): Observable<BrowseIntent> {
        return Observable.merge(initialIntent(), Observable.empty())
    }

    override fun render(state: BrowseViewState) {
        when {
            state.isLoading -> {
                setupScreenForLoadingState()
            }
            state is BrowseViewState.Failed -> {
                setupScreenForErrorState()
            }
            state is BrowseViewState.Success -> {
                setupScreenForSuccess(state.ticker)
            }
        }
    }

    private fun setupScreenForSuccess(ticker: Ticker?) {
        Timber.d("setupScreenForSuccess")
        progress.visibility = View.GONE
        if(ticker != null) {
            tickerText.text = ticker.priceUsd
            tickerText.visibility = View.VISIBLE
        }
    }

    private fun setupScreenForErrorState() {
        Timber.d("setupScreenForErrorState")
        // TODO show error state
        progress.visibility = View.GONE
        tickerText.visibility = View.GONE
    }

    private fun setupScreenForLoadingState() {
        Timber.d("setupScreenForLoadingState")
        progress.visibility = View.VISIBLE
        tickerText.visibility = View.GONE
    }

    /**
     * The initial Intent the [MviView] emit to convey to the [MviViewModel]
     * that it is ready to receive data.
     * This initial Intent is also used to pass any parameters the [MviViewModel] might need
     * to render the initial [MviViewState] (e.g. some id to load specific data]).
     */
    private fun initialIntent(): Observable<BrowseIntent.InitialIntent> {
        return Observable.just(BrowseIntent.InitialIntent)
    }
}