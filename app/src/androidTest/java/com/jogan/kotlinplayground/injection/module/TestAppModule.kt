package com.jogan.kotlinplayground.injection.module

import android.content.Context
import com.jogan.kotlinplayground.test.TestApplication
import dagger.Module
import dagger.Provides

@Module
object TestAppModule {

    @JvmStatic
    @Provides
    fun provideContext(application: TestApplication): Context {
        return application.applicationContext
    }
}