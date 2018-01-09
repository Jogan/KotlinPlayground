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