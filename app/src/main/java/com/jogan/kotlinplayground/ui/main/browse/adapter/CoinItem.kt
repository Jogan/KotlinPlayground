package com.jogan.kotlinplayground.ui.main.browse.adapter

import com.jogan.kotlinplayground.R
import com.jogan.kotlinplayground.data.model.Ticker
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder

import kotlinx.android.synthetic.main.item_coin.*

class CoinItem constructor(private val ticker: Ticker) : Item() {

    override fun getLayout(): Int {
        return R.layout.item_coin
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        // TODO load coin image viewHolder.coinImage

        viewHolder.coinTitleText.text = ticker.name
        viewHolder.coinPriceText.text = ticker.priceUsd
        viewHolder.coinPercentChangeText.text = "${ticker.percentChange24h}%"
    }
}