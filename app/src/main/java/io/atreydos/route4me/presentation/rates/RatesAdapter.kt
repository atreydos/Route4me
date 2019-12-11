package io.atreydos.route4me.presentation.rates

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.atreydos.route4me.R


class DocumentsAdapter(private val context: Context, private val listener: Listener) :
    ListAdapter<CurrencyRate, HeaderViewHolder>(PersonalDocumentsDiffCallback()) {

    interface Listener {
        fun onRateClicked(currencyName: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_exchange_rate, parent, false)
        return HeaderViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

data class CurrencyRate(val rate: Pair<String, Double>) {
    val id = rate.first.hashCode()
}


class HeaderViewHolder(itemView: View, private val listener: DocumentsAdapter.Listener) :
    RecyclerView.ViewHolder(itemView) {

    private val clRoot: ConstraintLayout = itemView.findViewById(R.id.clRoot)
    private val tvCurrencyName: TextView = itemView.findViewById(R.id.tvCurrencyName)
    private val tvExchangeRate: TextView = itemView.findViewById(R.id.tvExchangeRate)

    fun bind(item: CurrencyRate) {
        with(item.rate) {
            tvCurrencyName.text = first
            tvExchangeRate.text = String.format("%1$.2f", second)
            clRoot.setOnClickListener { listener.onRateClicked(first) }
        }
    }
}


class PersonalDocumentsDiffCallback : DiffUtil.ItemCallback<CurrencyRate>() {
    override fun areItemsTheSame(oldItem: CurrencyRate, newItem: CurrencyRate): Boolean {
        return oldItem.id == newItem.id && oldItem::class.java == newItem::class.java
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: CurrencyRate, newItem: CurrencyRate): Boolean {
        return oldItem == newItem
    }
}