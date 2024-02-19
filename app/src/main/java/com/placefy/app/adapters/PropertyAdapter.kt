package com.placefy.app.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.placefy.app.activities.PropertyShowActivity
import com.placefy.app.databinding.RvPropertiesItemBinding
import com.placefy.app.helpers.PropertyHelper
import com.placefy.app.models.data.Image
import com.placefy.app.models.data.Property

class PropertyAdapter(private val context: Context) :
    RecyclerView.Adapter<PropertyAdapter.ViewHolder>() {

    private var propertyList = mutableListOf<Property>()

    @SuppressLint("NotifyDataSetChanged")
    fun loadData(list: MutableList<Property>) {
        propertyList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: RvPropertiesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(property: Property) {

            val list = property.gallery?.map { image: Image ->
                image.thumbnail ?: ""
            }

            val adapter = ImageAdapter(context) {}
            adapter.loadData(list!!.toMutableList())
            binding.rvGallery.adapter = adapter

            val helper = PropertyHelper(property)
            val amenities = helper.composeAmenities()
            binding.propertyBeds.text = amenities["bedrooms"]
            binding.propertyBaths.text = amenities["bathrooms"]
            binding.propertyCars.text = amenities["cars"]
            binding.propertyTotalArea.text = amenities["totalArea"]
            binding.propertyValue.text = helper.composeTotalValue()
            binding.propertyAddressLine.text = helper.composeAddress()
            binding.propertyTitle.text = property.title
            binding.propertyType.text = if (property.type == "sale") "Venda" else "Aluguel"

            binding.btnShowProperty.setOnClickListener {
                val intent = Intent(context, PropertyShowActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("propertyId", property.id)
                ContextCompat.startActivity(context, intent, null)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val itemView = RvPropertiesItemBinding.inflate(
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