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
package com.jogan.kotlinplayground.data.db

import android.arch.persistence.room.Room
import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.jogan.kotlinplayground.data.ticker.Ticker
import com.jogan.kotlinplayground.test.util.TickerFactory
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlaygroundDatabaseTest {
    private lateinit var db: PlaygroundDatabase
    private lateinit var tickerDao: TickerDao

    @Before
    fun setUp() {
        val context: Context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(context, PlaygroundDatabase::class.java).build()
        tickerDao = db.tickerDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun writeTickerAndReadInList() {
        val ticker: Ticker = TickerFactory.makeTickerWithName("bitcoin")
        tickerDao.insertTickers(listOf(ticker))
        val byName: List<TickerTuple> = tickerDao.findAllTickersWithName("bitcoin").blockingFirst()
        val actual: TickerTuple = byName[0]

        assert(actual.id.equals(ticker.id))
        assert(actual.name.equals(ticker.name))
        assert(actual.symbol.equals(ticker.symbol))
    }
}