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

import com.jogan.kotlinplayground.data.ticker.Ticker
import io.reactivex.Flowable
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import io.reactivex.Single
import timber.log.Timber

@Dao
abstract class TickerDao {
    @Query("SELECT * FROM tickers")
    abstract fun getAllTickers(): Single<List<Ticker>>

    @Query("SELECT id, name, symbol FROM tickers")
    abstract fun getAllTickerTuples(): Flowable<List<TickerTuple>>

    @Query("SELECT * FROM tickers WHERE name LIKE :search OR symbol LIKE :search")
    abstract fun findAllTickersWithName(search: String): Flowable<List<TickerTuple>>

    @Query("SELECT COUNT(1) FROM tickers")
    abstract fun tickerCount(): Single<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertTicker(ticker: Ticker)

    @Update
    abstract fun updateTicker(ticker: Ticker)

    @Delete
    abstract fun deleteTickers(list: List<Ticker>)

    fun insertOrUpdateTicker(ticker: Ticker): Ticker {
        Timber.d("Inserting ticker: %s", ticker)
        insertTicker(ticker)
        return ticker
    }
}

class TickerTuple {
    @ColumnInfo(name = "id")
    var id: String? = null

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "symbol")
    var symbol: String? = null
}