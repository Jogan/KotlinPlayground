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
package com.jogan.kotlinplayground.injection.module

import android.content.Context
import com.jogan.kotlinplayground.AppInitializers
import com.jogan.kotlinplayground.LeakCanaryInitializer
import com.jogan.kotlinplayground.PlaygroundApplication
import com.jogan.kotlinplayground.TimberInitializer
import dagger.Module
import dagger.Provides

@Module
object AppModule {

    @JvmStatic
    @Provides
    fun provideContext(application: PlaygroundApplication): Context {
        return application.applicationContext
    }

    @JvmStatic
    @Provides
    fun provideAppInitializers(leakCanaryInitializer: LeakCanaryInitializer, timberInitializer: TimberInitializer): AppInitializers {
        return AppInitializers(leakCanaryInitializer, timberInitializer)
    }
}
