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
import android.support.annotation.LayoutRes
import android.support.constraint.ConstraintSet
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
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

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: BrowseViewModel

    private val disposables = CompositeDisposable()

    private var selectedView: View? = null

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
        setupAnimations();
        bind()
    }

    private fun setupAnimations() {
        selectedView = null

        root.setOnClickListener { toDefault() }

        tickerOneImage.setOnClickListener {
            if (selectedView != tickerOneImage) {
                updateConstraints(R.layout.fragment_browse_ticker_one)
                selectedView = tickerOneImage
            } else {
                toDefault()
            }
        }

        tickerTwoImage.setOnClickListener {
            if (selectedView != tickerTwoImage) {
                updateConstraints(R.layout.fragment_browse_ticker_two)
                selectedView = tickerTwoImage
            } else {
                toDefault()
            }
        }
    }

    private fun toDefault() {
        if (selectedView != null) {
            updateConstraints(R.layout.fragment_browse)
            selectedView = null
        }
    }

    private fun updateConstraints(@LayoutRes id: Int) {
        val newConstraintSet = ConstraintSet()
        newConstraintSet.clone(activity, id)
        newConstraintSet.applyTo(root)
        val transition = ChangeBounds()
        transition.interpolator = OvershootInterpolator()
        TransitionManager.beginDelayedTransition(root, transition)
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
                setupScreenForSuccess(state.tickers)
            }
        }
    }

    private fun setupScreenForSuccess(tickers: List<Ticker>?) {
        Timber.d("setupScreenForSuccess")
        progress.visibility = View.GONE
        tickerOneTitleText.visibility = View.VISIBLE
        tickerOneImage.visibility = View.VISIBLE
        tickerOnePriceText.visibility = View.VISIBLE
        tickerTwoTitleText.visibility = View.VISIBLE
        tickerTwoImage.visibility = View.VISIBLE
        tickerTwoPriceText.visibility = View.VISIBLE
        if (tickers != null) {
            val tickerOne = tickers.get(0)
            tickerOneTitleText.text = tickerOne.name
            tickerOnePriceText.text = tickerOne.priceUsd
            val tickerTwo = tickers.get(1)
            tickerTwoTitleText.text = tickerTwo.name
            tickerTwoPriceText.text = tickerTwo.priceUsd
        }
    }

    private fun setupScreenForErrorState() {
        Timber.d("setupScreenForErrorState")
        // TODO show error state
        progress.visibility = View.GONE
        tickerOneTitleText.visibility = View.GONE
        tickerOneImage.visibility = View.GONE
        tickerOnePriceText.visibility = View.GONE
        tickerTwoTitleText.visibility = View.GONE
        tickerTwoImage.visibility = View.GONE
        tickerTwoPriceText.visibility = View.GONE
    }

    private fun setupScreenForLoadingState() {
        Timber.d("setupScreenForLoadingState")
        progress.visibility = View.VISIBLE
        tickerOneTitleText.visibility = View.GONE
        tickerOneImage.visibility = View.GONE
        tickerOnePriceText.visibility = View.GONE
        tickerTwoTitleText.visibility = View.GONE
        tickerTwoImage.visibility = View.GONE
        tickerTwoPriceText.visibility = View.GONE
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