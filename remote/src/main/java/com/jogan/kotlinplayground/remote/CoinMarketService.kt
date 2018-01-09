package com.jogan.kotlinplayground.remote

import com.jogan.kotlinplayground.remote.model.TickerModel
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinMarketService {
    @GET("ticker/{id}")
    fun getTickerForCurrency(@Path("id") id: String): Flowable<TickerResponse>

    class TickerResponse {
        lateinit var ticker: List<TickerModel>
    }
}