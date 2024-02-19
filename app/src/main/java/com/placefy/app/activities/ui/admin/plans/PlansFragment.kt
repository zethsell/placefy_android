package com.placefy.app.activities.ui.admin.plans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import com.placefy.app.activities.ui.admin.IndexTopBarFragment
import com.placefy.app.adapters.PlansListAdapter
import com.placefy.app.api.RetrofitHelper
import com.placefy.app.api.interfaces.PlanAPI
import com.placefy.app.databinding.FragmentPlansBinding
import com.placefy.app.enums.Register
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PlansFragment : Fragment() {

    private lateinit var binding: FragmentPlansBinding

    private val base by lazy {
        RetrofitHelper(requireActivity().baseContext).authApi
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        load()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlansBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun load() {
        CoroutineScope(Dispatchers.IO).launch {
            val api = base.create(PlanAPI::class.java)
            val response = api.listAdmin()
            val plans = response.body()

            withContext(Dispatchers.Main) {

                val bundle = bundleOf(
                    "title" to "Todos os Planos",
                    "description" to "Lista de todos os planos cadastrados.",
                    "action" to Register.PLAN.ordinal
                )

                childFragmentManager.commit {
                    replace<IndexTopBarFragment>(
                        binding.fragPlansContainer.id, args = bundle
                    )
                }

                if (plans != null) {
                    val adapter = PlansListAdapter()
                    adapter.loadData(plans.toMutableList())
                    binding.rvPlans.adapter = adapter
                    binding.rvPlans.layoutManager = LinearLayoutManager(requireContext())

                }
            }
        }
    }
}