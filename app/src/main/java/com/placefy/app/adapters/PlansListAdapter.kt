package com.placefy.app.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.placefy.app.databinding.RvAdminListItemBinding
import com.placefy.app.enums.PlanType
import com.placefy.app.models.data.Plan

class PlansListAdapter :
    RecyclerView.Adapter<PlansListAdapter.ViewHolder>() {

    private var planList = mutableListOf<Plan>()

    @SuppressLint("NotifyDataSetChanged")
    fun loadData(list: MutableList<Plan>) {
        planList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: RvAdminListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(plan: Plan) {
            binding.itemTitle.text = plan.description
            binding.itemSubtitle.text = plan.type
            binding.btnActionType.text = "Visualizar"
        }

        private fun getType(type: String): String {
            return when (type) {
                PlanType.AGENCY.type -> "Imobiliária"
                PlanType.AGENT.type -> "Corretor"
                PlanType.PARTICULAR.type -> "Particular"
                else -> "Todos"
            }
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
        val plan = planList[position]
        holder.bind(plan)
    }

    override fun getItemCount(): Int {
        return planList.size
    }


}