package com.placefy.app.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.placefy.app.databinding.RvTaxesItemBinding
import com.placefy.app.models.data.PriceInformation

class TaxesAdapter :
    RecyclerView.Adapter<TaxesAdapter.ViewHolder>() {

    private var taxesList = mutableListOf<PriceInformation>()

    @SuppressLint("NotifyDataSetChanged")
    fun loadData(list: MutableList<PriceInformation>) {
        taxesList = list.filter {
            it.description != "value"
        }.toMutableList()
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: RvTaxesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tax: PriceInformation) {
            binding.taxName.text = tax.description
            binding.taxValue.text = tax.value.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val itemView = RvTaxesItemBinding.inflate(
            layoutInflater, parent, false
        )

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tax = taxesList[position]
        holder.bind(tax)
    }

    override fun getItemCount(): Int {
        return taxesList.size
    }
}