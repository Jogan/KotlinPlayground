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
package com.jogan.kotlinplayground.ui.home

import com.jogan.kotlinplayground.ui.base.mvi.MviResult

sealed class HomeResult : MviResult {
    sealed class SyncTickerResult : HomeResult() {
        data class Success(val success: Boolean) : SyncTickerResult() {
            override fun toString(): String = Success::class.java.simpleName
        }
        data class Failure(val error: Throwable) : SyncTickerResult() {
            override fun toString(): String = Failure::class.java.simpleName
        }
        object InFlight : SyncTickerResult() {
            override fun toString(): String = InFlight::class.java.simpleName
        }
    }
}