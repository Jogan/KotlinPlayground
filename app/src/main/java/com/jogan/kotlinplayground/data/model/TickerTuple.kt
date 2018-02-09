package com.jogan.kotlinplayground.data.model

import android.arch.persistence.room.ColumnInfo

class TickerTuple {
    @ColumnInfo(name = "id")
    var id: String? = null

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "symbol")
    var symbol: String? = null
}