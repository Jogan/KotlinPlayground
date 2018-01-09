package com.jogan.kotlinplayground.domain.model

/**
 * Representation for a [Ticker] fetched from an external layer data source
 */
data class Ticker(val id: String,
                        val name: String,
                        val symbol: String,
                        val rank: String,
                        val priceUsd: String,
                        val priceBtc: String,
                        val percentChange1h: String,
                        val percentChange24h: String,
                        val percentChange7d: String)