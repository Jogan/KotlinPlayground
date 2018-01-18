package com.jogan.kotlinplayground.ui.main.browse

import android.arch.lifecycle.ViewModel
import com.jogan.kotlinplayground.injection.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class BrowseBuilder {
    @ContributesAndroidInjector
    internal abstract fun browseFragment(): BrowseFragment

    @Binds
    @IntoMap
    @ViewModelKey(BrowseViewModel::class)
    abstract fun bindBrowseViewModel(viewModel: BrowseViewModel): ViewModel
}