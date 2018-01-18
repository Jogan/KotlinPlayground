package com.jogan.kotlinplayground.ui.main

import com.jogan.kotlinplayground.ui.main.browse.BrowseBuilder
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class MainBuilder {
    @ContributesAndroidInjector(modules = [
        BrowseBuilder::class
    ])
    internal abstract fun mainActivity(): MainActivity
}