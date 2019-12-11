package io.atreydos.route4me.presentation.rates

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import io.atreydos.route4me.presentation.common.BaseActivity
import io.atreydos.route4me.presentation.common.BaseFragment
import io.atreydos.route4me.presentation.common.annotion.Layout
import io.atreydos.route4me.R
import io.atreydos.route4me.domain.common.ODResult
import kotlinx.android.synthetic.main.fragment_rates.*
import org.koin.androidx.viewmodel.ext.android.viewModel

@Layout(R.layout.fragment_rates)
class RatesFragment : BaseFragment() {

    val viewModel: RatesViewModel by viewModel()

    private var adapter: DocumentsAdapter? = null

    private val ratesAdapterListener = object : DocumentsAdapter.Listener {
        override fun onRateClicked(currencyName: String) {
            val action =
                RatesFragmentDirections.actionRatesFragmentToRateHistoryGraphFragment(
                    currencyName
                )
            findNavController().navigate(action)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        srlRates.setOnRefreshListener { viewModel.reloadData() }
        rvRates.layoutManager = LinearLayoutManager(context)
        adapter = DocumentsAdapter(context!!, ratesAdapterListener)
        rvRates.adapter = adapter
        handleViewModel()
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as BaseActivity).setSupportActionBar(toolbar)
        viewModel.reloadData()
    }

    private fun handleViewModel() {
        viewModel.latestRates.observe(this, Observer { result ->
            when (result) {
                is ODResult.Loading -> {
                    srlRates.isRefreshing = true
                }
                is ODResult.Success -> {
                    srlRates.isRefreshing = false
                    val rates = ArrayList<CurrencyRate>(result.data.size).apply {
                        result.data.forEach { (k, v) ->
                            add(CurrencyRate(Pair(k, v)))
                        }
                    }
                    adapter?.submitList(rates)

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
}
