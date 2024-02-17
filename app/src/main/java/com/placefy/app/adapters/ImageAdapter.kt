package com.placefy.app.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.placefy.app.databinding.ImageListItemBinding

class ImageAdapter(private val context: Context, private val click: (String) -> Unit) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private var imageList = mutableListOf<String>()

    @SuppressLint("NotifyDataSetChanged")
    fun loadData(list: MutableList<String>) {
        imageList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: ImageListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imageView: ImageView = binding.listItemImage

        fun bind(image: String) {
            this.itemView.setOnClickListener {
                click(image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val itemView = ImageListItemBinding.inflate(
            layoutInflater, parent, false
        )

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(imageList.get(position)).into(holder.imageView)
        val image = imageList[position]
        holder.bind(image)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}