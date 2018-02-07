package com.jogan.kotlinplayground.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.jogan.kotlinplayground.data.ticker.Ticker

@Database(entities = [Ticker::class], version = 1)
abstract class PlaygroundDatabase : RoomDatabase() {
    abstract fun tickerDao(): TickerDao
}