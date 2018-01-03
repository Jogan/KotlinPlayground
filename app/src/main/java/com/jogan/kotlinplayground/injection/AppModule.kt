package com.jogan.kotlinplayground.injection

import android.content.Context
import com.jogan.kotlinplayground.AppInitializers
import com.jogan.kotlinplayground.LeakCanaryInitializer
import com.jogan.kotlinplayground.PlaygroundApplication
import com.jogan.kotlinplayground.TimberInitializer
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideContext(application: PlaygroundApplication): Context {
        return application.applicationContext
    }

    @Provides
    fun provideAppInitializers(leakCanaryInitializer: LeakCanaryInitializer, timberInitializer: TimberInitializer): AppInitializers {
        return AppInitializers(leakCanaryInitializer, timberInitializer)
    }
}

