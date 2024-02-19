package com.placefy.app.activities.ui.admin.usersToApprove

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
import com.placefy.app.adapters.UsersListAdapter
import com.placefy.app.api.RetrofitHelper
import com.placefy.app.api.interfaces.UserAPI
import com.placefy.app.databinding.FragmentUsersApproveBinding
import com.placefy.app.enums.Register
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsersApproveFragment : Fragment() {

    private lateinit var binding: FragmentUsersApproveBinding

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
        binding = FragmentUsersApproveBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun load() {
        CoroutineScope(Dispatchers.IO).launch {
            val api = base.create(UserAPI::class.java)
            val response = api.listToApprove()
            val users = response.body()

            withContext(Dispatchers.Main) {

                val bundle = bundleOf(
                    "title" to "Usuários à serem aprovados",
                    "description" to "Lista de todos os usuários à serem aprovados.",
                    "action" to Register.USER_TO_APPROVE.ordinal
                )

                childFragmentManager.commit {
                    replace<IndexTopBarFragment>(
                        binding.fragUsersContainer.id, args = bundle
                    )
                }

                if (users != null) {
                    val adapter = UsersListAdapter()
                    adapter.loadData(users.toMutableList())
                    binding.rvUsers.adapter = adapter
                    binding.rvUsers.layoutManager = LinearLayoutManager(requireContext())

                }
            }
        }
    }
}