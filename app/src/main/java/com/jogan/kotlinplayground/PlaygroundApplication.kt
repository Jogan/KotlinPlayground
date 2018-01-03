package com.jogan.kotlinplayground

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject


class PlaygroundApplication : DaggerApplication() {

    @Inject lateinit var initializers: AppInitializers

    override fun onCreate() {
        super.onCreate()
        initializers.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}