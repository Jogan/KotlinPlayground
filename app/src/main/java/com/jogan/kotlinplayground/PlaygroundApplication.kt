package com.jogan.kotlinplayground

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject


class PlaygroundApplication : DaggerApplication() {

    @Inject lateinit var managers: AppManagers

    override fun onCreate() {
        super.onCreate()
        managers.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}