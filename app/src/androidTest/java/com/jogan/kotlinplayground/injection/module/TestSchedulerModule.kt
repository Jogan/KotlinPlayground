package com.jogan.kotlinplayground.injection.module

import com.jogan.kotlinplayground.util.schedulers.BaseSchedulerProvider
import com.jogan.kotlinplayground.util.schedulers.ImmediateSchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestSchedulerModule {

    @JvmStatic
    @Provides
    @Singleton
    fun provideSchedulerProvider(): BaseSchedulerProvider {
        return ImmediateSchedulerProvider
    }
}