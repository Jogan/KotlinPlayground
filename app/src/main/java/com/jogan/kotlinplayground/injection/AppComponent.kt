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

import com.jogan.kotlinplayground.PlaygroundApplication
import com.jogan.kotlinplayground.injection.module.AppModule
import com.jogan.kotlinplayground.injection.module.ViewModelBuilder
import com.jogan.kotlinplayground.injection.module.DataModule
import com.jogan.kotlinplayground.injection.module.DatabaseModule
import com.jogan.kotlinplayground.injection.module.NetworkModule
import com.jogan.kotlinplayground.injection.module.SchedulerModule
import com.jogan.kotlinplayground.ui.home.HomeBuilder
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            AppModule::class,
            ViewModelBuilder::class,
            DataModule::class,
            DatabaseModule::class,
            HomeBuilder::class,
            NetworkModule::class,
            SchedulerModule::class
        ])
interface AppComponent : AndroidInjector<PlaygroundApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<PlaygroundApplication>()
}