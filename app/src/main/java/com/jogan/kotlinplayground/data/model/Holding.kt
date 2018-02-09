package com.jogan.kotlinplayground.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
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