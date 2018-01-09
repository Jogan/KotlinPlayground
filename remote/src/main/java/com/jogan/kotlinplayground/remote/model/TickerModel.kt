package com.jogan.kotlinplayground.remote.model

/**
 * Representation for a [TickerModel] fetched from the API
 */
class TickerModel(val id: String,
                  val name: String,
                  val symbol: String,
                  val rank: String,
                  val price_usd: String,
                  val price_btc: String,
                  val percent_change_1h: String,
                  val percent_change_24h: String,
                  val percent_change_7d: String)