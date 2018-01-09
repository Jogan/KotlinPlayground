package com.jogan.kotlinplayground.remote

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Provide "make" methods to create a single instance of the [CoinMarketService]
 * and its related dependencies, such as OkHttpClient, Moshi, etc.
 *
 * NOTE: marking this class as "object" creates a Singleton in Kotlin (refer to documentation on object declaration)
 */
object CoinMarketServiceFactory {

    fun makeCoinMarketService(isDebug: Boolean): CoinMarketService {
        val okHttpClient = makeOkHttpClient(makeLoggingInterceptor(isDebug))
        return makeCoinMarketService(okHttpClient, makeMoshi())
    }

    private fun makeCoinMarketService(okHttpClient: OkHttpClient, moshi: Moshi): CoinMarketService {
        val retrofit = Retrofit.Builder()
                .baseUrl(" https://api.coinmarketcap.com/v1/")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
        return retrofit.create(CoinMarketService::class.java)
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()
    }

    private fun makeMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return logging
    }
}