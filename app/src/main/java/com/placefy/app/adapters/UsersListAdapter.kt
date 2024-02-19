package com.placefy.app.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.placefy.app.databinding.RvAdminListItemBinding
import com.placefy.app.models.data.user.User

class UsersListAdapter :
    RecyclerView.Adapter<UsersListAdapter.ViewHolder>() {

    private var userList = mutableListOf<User>()

    @SuppressLint("NotifyDataSetChanged")
    fun loadData(list: MutableList<User>) {
        userList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: RvAdminListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.itemTitle.text = user.name.plus(" ${user.surname}")
            binding.itemSubtitle.text = user.email
            binding.btnActionType.text = user.type?.capitalize() ?: "Visualizar"
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
        val user = userList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}