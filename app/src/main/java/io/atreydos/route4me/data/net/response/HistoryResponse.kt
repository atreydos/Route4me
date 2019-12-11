package io.atreydos.route4me.data.net.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class HistoryResponse(
    @SerializedName("start_at") val startAt: Date,
    @SerializedName("end_at") val endAt: Date,
    @SerializedName("base") val baseCurrency: String,
    @SerializedName("rates") val rates: HashMap<Date, HashMap<String, Double>>
)