package com.placefy.app.activities.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.constraintlayout.widget.Guideline
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.placefy.app.R
import com.placefy.app.activities.ui.auth.signUpOptions.AgencyFragment
import com.placefy.app.activities.ui.auth.signUpOptions.AgentFragment
import com.placefy.app.activities.ui.auth.signUpOptions.ParticularFragment
import com.placefy.app.activities.ui.contracts.ISignUpFragment
import com.placefy.app.api.RetrofitHelper
import com.placefy.app.api.interfaces.SignUpAPI
import com.placefy.app.databinding.FragmentSignUpBinding
import com.placefy.app.models.api.auth.signup.SignUpAgencyRequest
import com.placefy.app.models.api.auth.signup.SignUpAgentRequest
import com.placefy.app.models.api.auth.signup.SignUpParticularRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {


    private val base by lazy {
        RetrofitHelper(requireContext()).noAuthApi
    }

    private lateinit var api: SignUpAPI

    private lateinit var itemSelected: Any

    private val agent = "Corretor"
    private val agency = "Imobiliária"
    private val particular = "Particular"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSignUpBinding.inflate(inflater, container, false)
        api = base.create(SignUpAPI::class.java)

        setProfileDropDown(binding)
        setBtnCancelEvents(binding)
        setBtnSignUpEvents(binding)

        return binding.root
    }

    private fun setProfileDropDown(binding: FragmentSignUpBinding) {
        val profileItems = listOf(agent, agency, particular)
        val autoComplete: AutoCompleteTextView = binding.autoComplete
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, profileItems)

        autoComplete.setAdapter(adapter)
        autoComplete.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, _, position, _ ->
                itemSelected = adapterView.getItemAtPosition(position)

                when (itemSelected) {
                    agency -> {
                        childFragmentManager.commit {
                            replace<AgencyFragment>(binding.fragmentContainerView2.id)
                        }
                    }

                    agent -> {
                        childFragmentManager.commit {
                            replace<AgentFragment>(binding.fragmentContainerView2.id)
                        }
                    }

                    particular -> {
                        childFragmentManager.commit {
                            replace<ParticularFragment>(binding.fragmentContainerView2.id)
                        }
                    }
                }
                val guideLine: Guideline = requireActivity().findViewById(R.id.guideline);
                guideLine.setGuidelineBegin(100)
            }
    }

    private fun setBtnCancelEvents(binding: FragmentSignUpBinding) {
        binding.btnCancel.setOnClickListener {
            redirect()
        }
    }

    private fun setBtnSignUpEvents(binding: FragmentSignUpBinding) {
        binding.btnSignUp.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val fragment: ISignUpFragment = when (itemSelected) {
                    agency -> binding.fragmentContainerView2.getFragment<AgencyFragment>()
                    agent -> binding.fragmentContainerView2.getFragment<AgentFragment>()
                    particular -> binding.fragmentContainerView2.getFragment<ParticularFragment>()
                    else -> null
                } ?: throw Error("Falha ao Cadastrar")
                fragment.signup()
            }
//            redirect()
        }
    }

    suspend fun signupAgency(data: SignUpAgencyRequest) {
        api.signUpAgency(data)
    }

    suspend fun signupAgent(data: SignUpAgentRequest) {
        api.signUpAgent(data)
    }

    suspend fun signupParticular(data: SignUpParticularRequest) {
        api.signUp(data)
    }

    private fun redirect() {
        val guideLine: Guideline = requireActivity().findViewById(R.id.guideline);
        guideLine.setGuidelineBegin(700)

        parentFragmentManager.commit {
            replace<LoginFragment>(R.id.fragmentContainerView)
        }
    }


}