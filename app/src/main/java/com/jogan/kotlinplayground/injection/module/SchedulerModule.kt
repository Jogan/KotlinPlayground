package com.jogan.kotlinplayground.injection.module

import com.jogan.kotlinplayground.util.schedulers.BaseSchedulerProvider
import com.jogan.kotlinplayground.util.schedulers.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object SchedulerModule {

    @JvmStatic
    @Provides
    @Singleton
    fun provideSchedulerProvider(): BaseSchedulerProvider {
        return SchedulerProvider
    }
}