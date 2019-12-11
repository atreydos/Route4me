package io.atreydos.route4me.data.net.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class LatestResponse(
    @SerializedName("base") val baseCurrency: String,
    @SerializedName("date") val date: Date,
    @SerializedName("rates") val rates: HashMap<String, Double>
)