package com.placefy.app.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.placefy.app.databinding.RvPropertiesItemBinding
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
//            binding.rvGallery.layoutManager = LinearLayoutManager(context)


            val amenities = composeAmenities(property)
            binding.propertyBeds.text = amenities["bedrooms"]
            binding.propertyBaths.text = amenities["bathrooms"]
            binding.propertyCars.text = amenities["cars"]
            binding.propertyTotalArea.text = amenities["totalArea"]
            binding.propertyValue.text = composeTotalValue(property)
            binding.propertyAddressLine.text = composeAddress(property)
            binding.propertyTitle.text = property.title
            binding.propertyType.text = if (property.type == "sale") "Venda" else "Aluguel"
        }

        private fun composeTotalValue(property: Property): String {
            var value: Double = 0.0

            if (property.priceInformation != null) {
                property.priceInformation.forEach { price ->
                    if (price.addToTotal) {
                        value += price.value
                    }
                }
            }

            return value.toString()
        }

        private fun composeAmenities(property: Property): Map<String, String> {
            var totalArea = 0
            var bedrooms = 0
            var cars = 0
            var bathrooms = 0

            if (property.amenities != null) {
                property.amenities.forEach { amenity ->
                    when (amenity.name) {
                        "totalArea" -> totalArea = amenity.qtd ?: 0
                        "bedrooms" -> bedrooms = amenity.qtd ?: 0
                        "cars" -> cars = amenity.qtd ?: 0
                        "bathrooms" -> bathrooms = amenity.qtd ?: 0
                    }
                }
            }

            return mapOf<String, String>(
                "totalArea" to totalArea.toString(),
                "bedrooms" to bedrooms.toString(),
                "cars" to cars.toString(),
                "bathrooms" to bathrooms.toString()
            )
        }

        private fun composeAddress(property: Property): String {
            var address = property.address?.addressLine ?: ""
            address = address.plus(", ").plus(property.address?.number ?: "S/N")

            if (property.address?.complement != "") {
                address = address.plus(", ").plus(property.address?.complement)
            }

            address = address.plus(" - ").plus(property.address?.suburb ?: "")
            address = address.plus(", ").plus(property.address?.city ?: "")
            return address.plus(" - ").plus(property.address?.state ?: "")
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