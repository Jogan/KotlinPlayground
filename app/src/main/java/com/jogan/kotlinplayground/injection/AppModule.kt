package com.jogan.kotlinplayground.injection

import android.content.Context
import com.jogan.kotlinplayground.AppManagers
import com.jogan.kotlinplayground.LeakCanaryManager
import com.jogan.kotlinplayground.PlaygroundApplication
import com.jogan.kotlinplayground.TimberManager
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideContext(application: PlaygroundApplication): Context {
        return application.applicationContext
    }

    @Provides
    fun provideAppManagers(leakCanaryManager: LeakCanaryManager, timberManager: TimberManager): AppManagers {
        return AppManagers(leakCanaryManager, timberManager)
    }
}

