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
package com.jogan.kotlinplayground.data.repository

import com.jogan.kotlinplayground.data.model.TickerEntity
import io.reactivex.Flowable

/**
 * Interface defining methods for the retrieving of Tickers from the network. This is to be implemented by the
 * remote layer, using this interface as a way of communicating.
 */
interface TickerRemote {

    fun getTickerForCurrency(id: String): Flowable<List<TickerEntity>>
}