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
package com.jogan.kotlinplayground.data.model

/**
 * Representation for a [TickerEntity] fetched from an external layer data source
 *
 * NOTE: marking the class with "data" gives us immutable and the compiler automatically derives some useful members
 * from the properties (equals/hashCode, toString, copy)
 */
data class TickerEntity(val id: String,
                        val name: String,
                        val symbol: String,
                        val rank: String,
                        val priceUsd: String,
                        val priceBtc: String,
                        val percentChange1h: String,
                        val percentChange24h: String,
                        val percentChange7d: String)