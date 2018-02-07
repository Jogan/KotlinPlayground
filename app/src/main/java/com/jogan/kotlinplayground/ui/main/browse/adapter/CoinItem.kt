package com.jogan.kotlinplayground.ui.main.browse.adapter

import android.graphics.Color
import com.jogan.kotlinplayground.R
import com.jogan.kotlinplayground.data.ticker.Ticker
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder

import kotlinx.android.synthetic.main.item_coin.*
import java.math.BigDecimal

class CoinItem constructor(private val ticker: Ticker) : Item() {

    override fun getLayout(): Int {
        return R.layout.item_coin
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        // TODO load coin image viewHolder.coinImage

        viewHolder.apply {
            coinTitleText.text = ticker.name
            coinPriceText.text = ticker.priceUsd
            coinPercentChangeText.text = "${ticker.percentChange24h}%"
            val color = if (ticker.percentChange24h.toBigDecimal() > BigDecimal.ZERO) Color.GREEN else Color.RED
            coinPercentChangeText.setTextColor(color)
        }
    }
}