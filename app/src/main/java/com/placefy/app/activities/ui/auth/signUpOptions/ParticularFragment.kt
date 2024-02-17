package com.placefy.app.activities.ui.auth.signUpOptions

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.placefy.app.R
import com.placefy.app.activities.ui.auth.SignUpFragment
import com.placefy.app.activities.ui.contracts.ISignUpFragment
import com.placefy.app.databinding.FragmentParticularBinding
import com.placefy.app.helpers.InputExtended
import com.placefy.app.models.api.auth.signup.SignUpParticularRequest
import com.placefy.app.models.data.Address


class ParticularFragment : Fragment(), ISignUpFragment {
    private lateinit var binding: FragmentParticularBinding
    private var showPass: Boolean = false
    private var showConfirmPass: Boolean = false
    private var validForm: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentParticularBinding.inflate(inflater, container, false)

        setShowPassEvents()
        setShowConfirmPassEvents()
        setCheckAllFields()

        return binding.root
    }

    override suspend fun signup() {
        if (!validForm) {
            return
        }

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

        val data = SignUpParticularRequest(
            binding.name.text.toString(),
            binding.surname.text.toString(),
            binding.email.text.toString(),
            binding.phone.text.toString(),
            binding.personDoc.text.toString(),
            binding.registration.text.toString(),
            binding.birthDate.text.toString(),
            binding.password.text.toString(),
            binding.confirmPassword.text.toString(),
            address,
        )

        parentFrag.signupParticular(data)
    }

    private fun setShowPassEvents() {
        binding.imgShowPass.setOnClickListener {
            showPass = !showPass
            binding.imgShowPass.setImageResource(if (showPass) R.drawable.ic_visibility_off_24 else R.drawable.ic_visibility_24)

            binding.password.inputType =
                if (showPass) InputType.TYPE_CLASS_TEXT else InputExtended.TYPE_PASSWORD_TEXT
        }
    }

    private fun setShowConfirmPassEvents() {
        binding.imgConfirmShowPass.setOnClickListener {
            showConfirmPass = !showConfirmPass
            binding.imgConfirmShowPass.setImageResource(if (showConfirmPass) R.drawable.ic_visibility_off_24 else R.drawable.ic_visibility_24)

            binding.confirmPassword.inputType =
                if (showConfirmPass) InputType.TYPE_CLASS_TEXT else InputExtended.TYPE_PASSWORD_TEXT
        }
    }

    private fun setCheckAllFields() {
        val fields = mapOf<TextInputLayout, TextInputEditText>(
            binding.tilName to binding.name,
            binding.tilSurname to binding.surname,
            binding.tilEmail to binding.email,
            binding.tilPhone to binding.phone,
            binding.tilPersonDoc to binding.personDoc,
            binding.tilRegistration to binding.registration,
            binding.tilBirthDate to binding.birthDate,
        )

        binding.tilPassword.editText?.text.toString()

        fields.forEach() {
            it.value.setOnFocusChangeListener { _, _ ->
                if (it.value.text.toString().isEmpty()) {
                    it.key.error = "Este campo não pode ficar vazio!"
                    validForm = false
                    return@setOnFocusChangeListener;
                }

                if (it.value.text.toString().isNotEmpty()) {
                    it.key.error = null
                    validForm = true
                    return@setOnFocusChangeListener;
                }
            }
        }

        binding.password.setOnFocusChangeListener { _, _ ->
            if (binding.password.text.toString().isEmpty()) {
                binding.tilPassword.error = "Este campo não pode ficar vazio!"
                setImageviewMargin(binding.imgShowPass, 150)
                validForm = false
                return@setOnFocusChangeListener;
            }

            if (binding.password.text.toString().length < 8) {
                binding.tilPassword.error = "A senha deve conter pelo menos 8 caracteres!"
                setImageviewMargin(binding.imgShowPass, 150)
                validForm = false
                return@setOnFocusChangeListener;
            }


//            var pattern =
//                """/^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/""".toRegex()
//                    .toPattern()
//            var matcher = pattern.matcher(binding.password.text.toString())
//
//            if (matcher.matches()) {
//                binding.tilPassword.error =
//                    "Senha deve conter pelo menos 1 letra maiúscula, 1 letra minúscula, 1 número e um simbolo!"
//                setImageviewMargin(binding.imgShowPass, 150)
//                validForm = false
//                return@setOnFocusChangeListener;
//            }


            if ((binding.password.text.toString() != binding.confirmPassword.text.toString())) {
                binding.tilPassword.error = "Senhas não coincidem!";
                setImageviewMargin(binding.imgShowPass, 150)
                validForm = false
                return@setOnFocusChangeListener;
            }

            if (binding.password.text.toString().isNotEmpty()) {
                binding.tilPassword.error = null
                setImageviewMargin(binding.imgShowPass)
                validForm = true
                return@setOnFocusChangeListener;
            }
        }

        binding.confirmPassword.setOnFocusChangeListener { _, _ ->
            if (binding.password.text.toString().isEmpty()) {
                binding.tilPassword.error = "Este campo não pode ficar vazio!"
                setImageviewMargin(binding.imgShowPass, 150)
                validForm = false
                return@setOnFocusChangeListener;
            }

            if (binding.password.text.toString() != binding.confirmPassword.text.toString()) {
                binding.tilPassword.error = "Senhas não coincidem!";
                setImageviewMargin(binding.imgShowPass, 150)
                validForm = false
                return@setOnFocusChangeListener;
            }

            if (binding.tilPassword.error == null
                && binding.confirmPassword.text.toString().isNotEmpty()
            ) {
                setImageviewMargin(binding.imgShowPass)
                binding.tilPassword.error = null
                validForm = true
                return@setOnFocusChangeListener;
            }
        }
    }

    private fun setImageviewMargin(
        imageView: ImageView,
        right: Int = 0,
    ) {

        val layoutParams = imageView.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.setMargins(
            imageView.marginLeft,
            imageView.marginTop,
            right,
            imageView.marginBottom
        )
        imageView.layoutParams = layoutParams
    }
}