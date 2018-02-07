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