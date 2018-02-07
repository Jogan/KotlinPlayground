/*
 * Copyright 2017 John Hogan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jogan.kotlinplayground.ui.main.browse

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import com.jogan.kotlinplayground.R
import com.jogan.kotlinplayground.data.ticker.Ticker
import com.jogan.kotlinplayground.ui.base.BaseFragment
import com.jogan.kotlinplayground.ui.base.mvi.MviView
import com.jogan.kotlinplayground.ui.main.browse.adapter.CoinItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_browse.*
import timber.log.Timber
import javax.inject.Inject

class BrowseFragment : BaseFragment(), MviView<BrowseIntent, BrowseViewState> {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: BrowseViewModel

    private val disposables = CompositeDisposable()
    private val refreshIntentPublisher = PublishSubject.create<BrowseIntent.RefreshIntent>()

    // Views
    private val groupAdapter = GroupAdapter<ViewHolder>()
    private lateinit var groupLayoutManager: LinearLayoutManager

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
        setupViews()
        bind()
    }

    override fun onResume() {
        super.onResume()
        // conflicting with the initial intent but needed when coming back from the
        // an "update" activity to refresh the list.
        //refreshIntentPublisher.onNext(BrowseIntent.RefreshIntent(false))
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

    private fun setupViews() {
        groupLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.apply {
            layoutManager = groupLayoutManager
            adapter = groupAdapter
        }
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

    override fun intents(): Observable<BrowseIntent> {
        return Observable.merge(initialIntent(), refreshIntent())
    }

    private fun refreshIntent(): Observable<BrowseIntent.RefreshIntent> {
        return RxSwipeRefreshLayout.refreshes(swipeRefreshLayout)
                .map { BrowseIntent.RefreshIntent(false, 0 /* TODO load correct offset/start */) }
                .mergeWith(refreshIntentPublisher)
    }

    override fun render(state: BrowseViewState) {
        swipeRefreshLayout.isRefreshing = state.isLoading

        when {
            state.isLoading -> {
                setupScreenForLoadingState()
            }
            state is BrowseViewState.Failed -> {
                setupScreenForErrorState()
            }
            state is BrowseViewState.Success -> {
                setupScreenForSuccess(state.tickers)
            }
        }
    }

    private fun setupScreenForSuccess(tickers: List<Ticker>?) {
        Timber.d("setupScreenForSuccess")
        progress.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        // Add tickers to our list
        tickers?.forEach {
            CoinItem(it).apply {
                groupAdapter.add(this)
            }
        }
    }

    private fun setupScreenForErrorState() {
        Timber.d("setupScreenForErrorState")
        // TODO show error state
        progress.visibility = View.GONE
        recyclerView.visibility = View.GONE
    }

    private fun setupScreenForLoadingState() {
        Timber.d("setupScreenForLoadingState")
        progress.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
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