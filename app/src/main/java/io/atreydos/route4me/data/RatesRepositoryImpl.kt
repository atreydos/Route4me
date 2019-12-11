package io.atreydos.route4me.data

import io.atreydos.route4me.data.db.AppDatabase
import io.atreydos.route4me.data.db.entity.ExchangeRate
import io.atreydos.route4me.data.net.ApiService
import io.atreydos.route4me.data.net.util.toApiFormat
import io.atreydos.route4me.data.prefs.AppPrefs
import io.atreydos.route4me.domain.RatesRepository
import io.atreydos.route4me.domain.common.ODResult
import java.lang.Exception
import java.util.*
import kotlin.collections.HashMap

class RatesRepositoryImpl(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase,
    private val appPreferences: AppPrefs
) : RatesRepository {

    private val isCacheValid: Boolean
        get() {
            val timePassed = System.currentTimeMillis() - appPreferences.exchangeRatesTimestamp
            return timePassed < CACHE_VALIDITY_TIME
        }


    override suspend fun getLatestRates(): ODResult<HashMap<String, Double>> {
        return try {
            val rates: HashMap<String, Double>
            if (isCacheValid) {
                rates = appDatabase.exchangeRatesDao().getAll().convertToHashMap()
            } else {
                rates = apiService.latest().rates
                appPreferences.exchangeRatesTimestamp = System.currentTimeMillis()
                appDatabase.exchangeRatesDao().insert(rates.convertToExchangeRateList())
            }
            ODResult.Success(rates)
        } catch (e: Exception) {
            ODResult.Error(e)
        }
    }

    override suspend fun getRateHistory(
        startAt: Date,
        endAt: Date,
        targetCurrencySymbol: String
    ): ODResult<HashMap<Date, HashMap<String, Double>>> {
        return try {
            val rates = apiService.history(
                startAt = startAt.toApiFormat(),
                endAt = endAt.toApiFormat(),
                targetCurrencySymbol = targetCurrencySymbol
            ).rates
            ODResult.Success(rates)
        } catch (e: Exception) {
            ODResult.Error(e)
        }
    }


    private fun Array<ExchangeRate>.convertToHashMap(): HashMap<String, Double> {
        return HashMap<String, Double>().apply {
            this@convertToHashMap.forEach { (_, symbol, rate) ->
                this[symbol] = rate
            }
        }
    }

    private fun HashMap<String, Double>.convertToExchangeRateList(): ArrayList<ExchangeRate> {
        return ArrayList<ExchangeRate>(size).apply {
            this@convertToExchangeRateList.forEach { (symbol, rate) ->
                add(ExchangeRate(symbol = symbol, rate = rate))
            }
        }
    }

    companion object {
        private const val CACHE_VALIDITY_TIME = 1000 * 60 * 10 //10 minutes
    }
}