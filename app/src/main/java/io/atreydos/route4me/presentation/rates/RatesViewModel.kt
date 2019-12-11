package io.atreydos.route4me.presentation.rates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import io.atreydos.route4me.domain.RatesRepository
import io.atreydos.route4me.domain.common.ODResult
import io.atreydos.route4me.presentation.common.BaseViewModel
import kotlinx.coroutines.Dispatchers

class RatesViewModel(
    private val ratesRepository: RatesRepository
) : BaseViewModel() {

    private val _reloadTrigger = MutableLiveData<Boolean>()

    var latestRates: LiveData<ODResult<HashMap<String, Double>>> =
        Transformations.switchMap(_reloadTrigger) {
            liveData(Dispatchers.IO) {
                emit(ODResult.Loading())
                emit(ratesRepository.getLatestRates())
            }
        }

    fun reloadData() {
        _reloadTrigger.postValue(true)
    }
}