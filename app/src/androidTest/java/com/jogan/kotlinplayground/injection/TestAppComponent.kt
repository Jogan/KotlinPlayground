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
package com.jogan.kotlinplayground.injection

import android.app.Application
import com.jogan.kotlinplayground.data.ticker.ITickerRepository
import com.jogan.kotlinplayground.injection.module.DatabaseModule
import com.jogan.kotlinplayground.injection.module.TestAppModule
import com.jogan.kotlinplayground.injection.module.TestDataModule
import com.jogan.kotlinplayground.injection.module.ViewModelBuilder
import com.jogan.kotlinplayground.injection.module.TestSchedulerModule
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

    fun tickerRepository(): ITickerRepository

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TestAppComponent
    }
}