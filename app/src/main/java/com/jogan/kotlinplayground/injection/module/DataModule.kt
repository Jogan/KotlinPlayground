/*
 * Copyright 2017 John Hogan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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