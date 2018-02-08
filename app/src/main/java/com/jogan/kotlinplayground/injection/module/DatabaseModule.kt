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

import android.app.Application
import android.arch.persistence.room.Room
import com.jogan.kotlinplayground.data.db.PlaygroundDatabase
import com.jogan.kotlinplayground.data.db.TickerDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @JvmStatic
    @Provides
    @Singleton
    fun provideDatabase(application: Application): PlaygroundDatabase =
            Room.databaseBuilder(application, PlaygroundDatabase::class.java, "playground.db")
                    .fallbackToDestructiveMigration()
                    .build()

    @JvmStatic
    @Provides
    fun provideTickerDao(db: PlaygroundDatabase): TickerDao = db.tickerDao()
}