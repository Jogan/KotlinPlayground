package com.jogan.kotlinplayground.data.db

import androidx.room.TypeConverter
import java.math.BigDecimal


class Converters {
    @TypeConverter
    fun fromLong(value: Long): BigDecimal = BigDecimal(value).divide(BigDecimal(100))

    @TypeConverter
    fun toLong(bigDecimal: BigDecimal): Long = bigDecimal.multiply(BigDecimal(100)).toLong()
}