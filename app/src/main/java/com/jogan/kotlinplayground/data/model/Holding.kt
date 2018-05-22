package com.jogan.kotlinplayground.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "holdings")
data class Holding(@PrimaryKey val id: String,
                   val name: String,
                   val symbol: String,
                   val priceUsd: String?,
                   val percentChange1h: String?,
                   val percentChange24h: String?,
                   val percentChange7d: String?,
                   val totalAmountHeld: BigDecimal)