package com.placefy.app.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.placefy.app.databinding.RvAdminListItemBinding
import com.placefy.app.models.data.Property

class PropertyListAdapter :
    RecyclerView.Adapter<PropertyListAdapter.ViewHolder>() {

    private var propertyList = mutableListOf<Property>()

    @SuppressLint("NotifyDataSetChanged")
    fun loadData(list: MutableList<Property>) {
        propertyList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: RvAdminListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(property: Property) {
            binding.itemTitle.text = property.title
            binding.itemSubtitle.text = property.description
            binding.btnActionType.text = "Visualizar"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val itemView = RvAdminListItemBinding.inflate(
            layoutInflater, parent, false
        )

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val property = propertyList[position]
        holder.bind(property)
    }

    override fun getItemCount(): Int {
        return propertyList.size
    }
}