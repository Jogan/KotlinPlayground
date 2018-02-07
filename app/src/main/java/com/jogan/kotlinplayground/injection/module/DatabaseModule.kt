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
            Room.databaseBuilder(application, PlaygroundDatabase::class.java, "playground.db").build()


    @JvmStatic
    @Provides
    fun provideTickerDao(db: PlaygroundDatabase): TickerDao = db.tickerDao()
}