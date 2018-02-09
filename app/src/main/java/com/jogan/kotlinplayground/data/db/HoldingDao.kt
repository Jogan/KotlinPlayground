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
package com.jogan.kotlinplayground.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.jogan.kotlinplayground.data.model.Holding
import io.reactivex.Single

@Dao
abstract class HoldingDao {
    @Query("SELECT * FROM holdings")
    abstract fun getAllHoldings(): Single<List<Holding>>

    @Query("SELECT COUNT(*) FROM holdings")
    abstract fun holdingsCount(): Single<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertHolding(holding: Holding)

    @Update
    abstract fun updateHolding(holding: Holding)

    @Delete
    abstract fun deleteHolding(holding: Holding)
}