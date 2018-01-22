package com.jogan.kotlinplayground.injection.module

import com.jogan.kotlinplayground.api.CoinMarketService
import com.jogan.kotlinplayground.api.mapper.TickerMapper
import com.jogan.kotlinplayground.data.ticker.ITickerRepository
import com.jogan.kotlinplayground.data.ticker.TickerDataSource
import com.jogan.kotlinplayground.data.ticker.TickerRepository
import com.jogan.kotlinplayground.data.ticker.remote.TickerRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataModule {

    @JvmStatic
    @Provides
    @Singleton
    fun provideTickerMapper(): TickerMapper {
        return TickerMapper()
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideTickerRemoteDataSource(coinMarketService: CoinMarketService, tickerMapper: TickerMapper): TickerDataSource {
        return TickerRemoteDataSource(coinMarketService, tickerMapper)
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideTickerRepository(tickerRemoteDataSource: TickerRemoteDataSource): ITickerRepository {
        return TickerRepository(tickerRemoteDataSource)
    }
}