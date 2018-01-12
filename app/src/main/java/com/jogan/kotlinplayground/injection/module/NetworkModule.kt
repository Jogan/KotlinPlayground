package com.jogan.kotlinplayground.injection.module

import com.jogan.kotlinplayground.BuildConfig
import com.jogan.kotlinplayground.api.CoinMarketService
import com.jogan.kotlinplayground.api.CoinMarketServiceFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object NetworkModule {

    @JvmStatic
    @Provides
    @Singleton
    fun provideCoinMarketService(): CoinMarketService {
        return CoinMarketServiceFactory.makeCoinMarketService(BuildConfig.DEBUG)
    }
}