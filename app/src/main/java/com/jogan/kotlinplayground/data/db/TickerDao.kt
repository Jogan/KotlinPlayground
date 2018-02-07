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

@Dao
interface TickerDao {
    @Query("SELECT * FROM tickers")
    fun getAllTickers(): Flowable<List<Ticker>>

    @Query("SELECT id, name, symbol FROM tickers")
    fun getAllTickerTuples(): Flowable<List<TickerTuple>>

    @Query("SELECT * FROM tickers WHERE name LIKE :search OR symbol LIKE :search")
    fun findAllTickersWithName(search: String): Flowable<List<TickerTuple>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTickers(list: List<Ticker>)

    @Update
    fun updateTickers(list: List<Ticker>)

    @Delete
    fun deleteTickers(list: List<Ticker>)
}

class TickerTuple {
    @ColumnInfo(name = "id")
    var id: String? = null

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "symbol")
    var symbol: String? = null
}