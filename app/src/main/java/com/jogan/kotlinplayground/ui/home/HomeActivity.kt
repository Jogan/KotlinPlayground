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
package com.jogan.kotlinplayground.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import android.view.View
import com.jogan.kotlinplayground.R
import com.jogan.kotlinplayground.ui.base.BaseActivity
import com.jogan.kotlinplayground.ui.base.mvi.MviView
import com.jogan.kotlinplayground.ui.home.browse.BrowseFragment
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_home.*
import timber.log.Timber
import javax.inject.Inject

class HomeActivity : BaseActivity(), MviView<HomeIntent, HomeViewState> {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: HomeViewModel

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(HomeViewModel::class.java)

        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        bind()
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }

    private fun bind() {
        // Subscribe to the ViewModel and call render for every emitted state
        disposables.add(viewModel.states().subscribe({ render(it) }))
        // Pass the UI's intents to the ViewModel
        viewModel.processIntents(intents())
    }

    override fun intents(): Observable<HomeIntent> {
        return Observable.merge(initialIntent(), Observable.empty())
    }

    override fun render(state: HomeViewState) {
        when {
            state.isLoading -> {
                setupScreenForLoadingState()
            }
            state is HomeViewState.Failed -> {
                Timber.e(state.error, "error")
                setupScreenForErrorState()
            }
            state is HomeViewState.Success -> {
                // we have data in DB
                setupScreenForSuccess()
            }
        }
    }

    private fun setupScreenForSuccess() {
        Timber.d("setupScreenForSuccess")
        progress.visibility = View.GONE
        contentFrame.visibility = View.VISIBLE
        if (supportFragmentManager.findFragmentById(R.id.contentFrame) == null) {
            addFragmentToActivity(supportFragmentManager, BrowseFragment(), R.id.contentFrame)
        }
    }

    private fun setupScreenForErrorState() {
        Timber.d("setupScreenForErrorState")
        progress.visibility = View.GONE
        contentFrame.visibility = View.VISIBLE
        // TODO show error
    }

    private fun setupScreenForLoadingState() {
        Timber.d("setupScreenForLoadingState")
        progress.visibility = View.VISIBLE
        contentFrame.visibility = View.GONE
    }

    /**
     * The initial Intent the [MviView] emit to convey to the [MviViewModel]
     * that it is ready to receive data.
     * This initial Intent is also used to pass any parameters the [MviViewModel] might need
     * to render the initial [MviViewState] (e.g. some id to load specific data]).
     */
    private fun initialIntent(): Observable<HomeIntent.InitialIntent> {
        return Observable.just(HomeIntent.InitialIntent)
    }

    @SuppressLint("CommitTransaction")
    private fun addFragmentToActivity(
        fragmentManager: androidx.fragment.app.FragmentManager,
        fragment: androidx.fragment.app.Fragment,
        @IdRes frameId: Int
    ) {
        fragmentManager.beginTransaction().run {
            add(frameId, fragment)
            commit()
        }
    }
}
