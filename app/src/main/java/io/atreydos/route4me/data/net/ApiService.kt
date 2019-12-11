package io.atreydos.route4me.data.net

import io.atreydos.route4me.data.net.response.*
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API docs can be found by attached URL
 * @see <a href="https://exchangeratesapi.io</a>
 */
interface ApiService {

    companion object {
        private const val DEFAULT_CURRENCY = "USD"
    }

    /**
     * Rates are quoted against the USD by default.
     * Quote against a different currency by setting the base parameter in your request.
     */
    @GET("/latest")
    suspend fun latest(@Query("base") baseCurrencySymbol: String = DEFAULT_CURRENCY): LatestResponse


    /**
     * History are quoted against the USD by default.
     * Get historical rates for a time period.
     */
    @GET("/history")
    suspend fun history(
        @Query("start_at") startAt: String,
        @Query("end_at") endAt: String,
        @Query("base") baseCurrencySymbol: String = DEFAULT_CURRENCY,
        @Query("symbols") targetCurrencySymbol: String
    ): HistoryResponse
}