package com.placefy.app.activities.ui.auth.signUpOptions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.placefy.app.activities.ui.auth.SignUpFragment
import com.placefy.app.activities.ui.contracts.ISignUpFragment
import com.placefy.app.databinding.FragmentAgencyBinding
import com.placefy.app.models.api.auth.signup.SignUpAgencyRequest
import com.placefy.app.models.data.Address


class AgencyFragment : Fragment(), ISignUpFragment {

    private lateinit var binding: FragmentAgencyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAgencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override suspend fun signup() {
        val parentFrag: SignUpFragment = parentFragment as SignUpFragment
        val address = Address(
            binding.zipcode.text.toString(),
            binding.addressLine.text.toString(),
            binding.number.text.toString(),
            binding.complement.text.toString(),
            binding.suburb.text.toString(),
            binding.city.text.toString(),
            binding.state.text.toString(),
        )

        val data = SignUpAgencyRequest(
            binding.name.text.toString(),
            binding.surname.text.toString(),
            binding.email.text.toString(),
            binding.phone.text.toString(),
            binding.personDoc.text.toString(),
            binding.registration.text.toString(),
            binding.creci.text.toString(),
            address,
        )
        parentFrag.signupAgency(data)
    }
}