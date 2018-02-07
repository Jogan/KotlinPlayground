package com.jogan.kotlinplayground.data.db

import android.arch.persistence.room.*
import com.jogan.kotlinplayground.data.ticker.Ticker
import io.reactivex.Flowable
import android.arch.persistence.room.ColumnInfo


@Dao
public interface TickerDao {
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