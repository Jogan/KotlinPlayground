package com.jogan.kotlinplayground.injection

import android.app.Application
import android.arch.persistence.room.Database
import com.jogan.kotlinplayground.data.ticker.ITickerRepository
import com.jogan.kotlinplayground.injection.module.*
import com.jogan.kotlinplayground.test.TestApplication
import com.jogan.kotlinplayground.ui.main.MainBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            TestAppModule::class,
            AndroidSupportInjectionModule::class,
            ViewModelBuilder::class,
            DatabaseModule::class,
            MainBuilder::class,
            TestDataModule::class,
            TestSchedulerModule::class
        ])
interface TestAppComponent : AndroidInjector<TestApplication> {

    fun tickerRepository() : ITickerRepository

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): TestAppComponent
    }
}