package io.atreydos.route4me.domain

import io.atreydos.route4me.domain.common.ODResult
import java.util.*


interface RatesRepository {

    suspend fun getLatestRates(): ODResult<HashMap<String, Double>>

    suspend fun getRateHistory(
        startAt: Date,
        endAt: Date,
        targetCurrencySymbol: String
    ): ODResult<HashMap<Date, HashMap<String, Double>>>
}