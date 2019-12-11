package io.atreydos.route4me.presentation.details

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import io.atreydos.route4me.R
import io.atreydos.route4me.data.net.util.toApiFormat
import io.atreydos.route4me.domain.common.ODResult
import io.atreydos.route4me.presentation.common.BaseActivity
import io.atreydos.route4me.presentation.common.BaseFragment
import io.atreydos.route4me.presentation.common.annotion.Layout
import kotlinx.android.synthetic.main.fragment_rate_history.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


@Layout(R.layout.fragment_rate_history)
class RateHistoryFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    private val args: RateHistoryFragmentArgs by navArgs()
    val viewModel: RateHistoryViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar.subtitle = getString(R.string.toolbar_subtitle_rate_history, args.currencyCode)
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        srlRates.setOnRefreshListener(this)
        chart.setNoDataText(getString(R.string.warning_no_history))
        chart.xAxis.apply {
            position = XAxisPosition.BOTTOM
            setDrawGridLines(false)
        }
        chart.setPinchZoom(false)
        chart.description.isEnabled = false
        chart.isDoubleTapToZoomEnabled = false
        chart.legend.isEnabled = false
        chart.invalidate()

        handleViewModel()
        onRefresh()
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as BaseActivity).setSupportActionBar(toolbar)
    }


    override fun onRefresh() {
        viewModel.reloadData(args.currencyCode)
    }

    private fun handleViewModel() {
        viewModel.rateHistory.observe(this, Observer { result ->
            when (result) {
                is ODResult.Loading -> {
                    srlRates.isRefreshing = true
                }
                is ODResult.Success -> {
                    srlRates.isRefreshing = false
                    if (result.data.size == 0)
                        showAlert(getString(R.string.warning_no_history))
                    else {
                        fillChart(result.data)
                    }

                }
                is ODResult.Error -> {
                    srlRates.isRefreshing = false
                    with(result.exception) {
                        showAlert(localizedMessage ?: toString())
                        printStackTrace()
                    }
                }
            }
        })
    }


    private fun fillChart(rates: HashMap<Date, HashMap<String, Double>>) {
        val ratesSorted = rates.toSortedMap()
        val values: ArrayList<BarEntry> = ArrayList(ratesSorted.size)
        val title = "${ratesSorted.keys.first().toApiFormat()} - ${ratesSorted.keys.last().toApiFormat()}"

        var i = 0f
        ratesSorted.forEach { (date, rate) ->
            val value = rate.values.first().toFloat()
            values.add(BarEntry(i, value))
            i++
        }

        val set1 = BarDataSet(values, title).apply {
            setColors(Color.CYAN)
            setDrawValues(true)
        }

        val dataSets = ArrayList<IBarDataSet>().apply {
            add(set1)
        }

        chart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val position = value.toInt()
                val date = ratesSorted.keys.toList()[position]
                return date.toHumanReadableFormat()
            }
        }

        chart.data = BarData(dataSets)
        chart.setFitBars(true)
        chart.invalidate()
    }

    private fun Date.toHumanReadableFormat(): String {
        return SimpleDateFormat("MMM dd", Locale.getDefault()).format(this)
    }
}
