package io.atreydos.route4me.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import io.atreydos.route4me.data.db.entity.ExchangeRate

@Dao
interface ExchangeRatesDao : BaseDao<ExchangeRate> {

    @Query("SELECT * FROM rates")
    fun getAll(): Array<ExchangeRate>
}