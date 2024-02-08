package com.placefy.app.activities.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.placefy.app.R
import com.placefy.app.activities.ui.auth.signUpOptions.AgencyFragment
import com.placefy.app.activities.ui.auth.signUpOptions.AgentFragment
import com.placefy.app.activities.ui.auth.signUpOptions.ParticularFragment
import com.placefy.app.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSignUpBinding.inflate(inflater, container, false)
        setProfileDropDown(binding)
        setBtnCancelEvents(binding)

        return binding.root

    }


    private fun setProfileDropDown(binding: FragmentSignUpBinding) {
        val agent = "Corretor"
        val agency = "Imobiliária"
        val particular = "Particular"
        val profileItems = listOf(agent, agency, particular)
        val autoComplete: AutoCompleteTextView = binding.autoComplete
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, profileItems)

        autoComplete.setAdapter(adapter)
        autoComplete.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, _, position, _ ->
                val itemSelected = adapterView.getItemAtPosition(position)

                if (itemSelected === agent) {
                    childFragmentManager.commit {
                        replace<AgentFragment>(binding.fragmentContainerView2.id)
                    }
                }

                if (itemSelected === agency) {
                    childFragmentManager.commit {
                        replace<AgencyFragment>(binding.fragmentContainerView2.id)
                    }
                }

                if (itemSelected === particular) {
                    childFragmentManager.commit {
                        replace<ParticularFragment>(binding.fragmentContainerView2.id)
                    }
                }
            }
    }

    private fun setBtnCancelEvents(binding: FragmentSignUpBinding) {
        binding.btnCancel.setOnClickListener {

            parentFragmentManager.commit {
                replace<LoginFragment>(R.id.fragmentContainerView)
            }
        }
    }

    private fun setBtnSignUpEvents(binding: FragmentSignUpBinding) {
        binding.btnSignUp.setOnClickListener {

            parentFragmentManager.commit {
                replace<SignUpFragment>(R.id.fragmentContainerView)
            }
        }
    }

}