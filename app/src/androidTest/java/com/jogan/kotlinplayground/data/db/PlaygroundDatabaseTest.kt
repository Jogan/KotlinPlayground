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