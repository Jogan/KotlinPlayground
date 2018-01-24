package com.jogan.kotlinplayground.test

import android.support.test.InstrumentationRegistry
import com.jogan.kotlinplayground.injection.DaggerTestAppComponent
import com.jogan.kotlinplayground.injection.TestAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


class TestApplication : DaggerApplication() {

    private lateinit var testAppComponent: TestAppComponent

    companion object {
        fun appComponent(): TestAppComponent {
            return (InstrumentationRegistry.getTargetContext().applicationContext as TestApplication).testAppComponent
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        testAppComponent = DaggerTestAppComponent.builder().application(this).build()
        testAppComponent.inject(this)
        return testAppComponent
    }

}