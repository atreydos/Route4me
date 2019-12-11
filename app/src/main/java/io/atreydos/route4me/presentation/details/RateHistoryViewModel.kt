package io.atreydos.route4me.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import io.atreydos.route4me.domain.RatesRepository
import io.atreydos.route4me.domain.common.ODResult
import io.atreydos.route4me.presentation.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import java.util.*
import kotlin.collections.HashMap

class RateHistoryViewModel(
    private val ratesRepository: RatesRepository
) : BaseViewModel() {


    private val _reloadTrigger = MutableLiveData<HistoryOptions>()

    var rateHistory: LiveData<ODResult<HashMap<Date, HashMap<String, Double>>>> =
        Transformations.switchMap(_reloadTrigger) {
            liveData(Dispatchers.IO) {
                emit(ODResult.Loading())
                with(it) {
                    emit(ratesRepository.getRateHistory(startAt, endAt, targetCurrencySymbol))
                }
            }
        }


    fun reloadData(targetCurrencySymbol: String) {
        val options = HistoryOptions(
            startAt = Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 7),
            endAt = Date(System.currentTimeMillis()),
            targetCurrencySymbol = targetCurrencySymbol
        )
        _reloadTrigger.postValue(options)
    }
}

data class HistoryOptions(
    val startAt: Date,
    val endAt: Date,
    val targetCurrencySymbol: String
)