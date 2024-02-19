package com.placefy.app.activities.ui.admin.properties

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
import com.placefy.app.adapters.PropertyListAdapter
import com.placefy.app.api.RetrofitHelper
import com.placefy.app.api.interfaces.PropertyAPI
import com.placefy.app.databinding.FragmentPropertyBinding
import com.placefy.app.enums.Register
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PropertyFragment : Fragment() {

    private lateinit var binding: FragmentPropertyBinding

    private val base by lazy {
        RetrofitHelper(requireActivity().baseContext).authApi
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        load()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPropertyBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun load() {
        CoroutineScope(Dispatchers.IO).launch {
            val api = base.create(PropertyAPI::class.java)
            val response = api.list()
            val properties = response.body()

            withContext(Dispatchers.Main) {

                val bundle = bundleOf(
                    "title" to "Todos os Propriedades",
                    "description" to "Lista de todas as propriedades cadastradas.",
                    "action" to Register.PROPERTY.ordinal
                )

                childFragmentManager.commit {
                    replace<IndexTopBarFragment>(
                        binding.fragPropertiesContainer.id, args = bundle
                    )
                }

                if (properties != null) {
                    val adapter = PropertyListAdapter()
                    adapter.loadData(properties.toMutableList())
                    binding.rvProperties.adapter = adapter
                    binding.rvProperties.layoutManager = LinearLayoutManager(requireContext())

                }
            }
        }
    }


}