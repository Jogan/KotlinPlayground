package com.jogan.kotlinplayground.injection.module

import com.jogan.kotlinplayground.data.ticker.ITickerRepository
import com.jogan.kotlinplayground.data.ticker.TickerDataSource
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestDataModule {

    @JvmStatic
    @Provides
    @Singleton
    fun provideTickerRemoteDataSource(): TickerDataSource {
        return mock()
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideTickerRepository(): ITickerRepository {
        return mock()
    }
}