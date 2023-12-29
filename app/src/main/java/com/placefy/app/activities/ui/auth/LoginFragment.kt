package com.placefy.app.activities.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.placefy.app.R
import com.placefy.app.activities.AdminActivity
import com.placefy.app.activities.ui.dialog.SuccessDialog
import com.placefy.app.api.RetrofitHelper
import com.placefy.app.api.interfaces.LoginAPI
import com.placefy.app.databinding.FragmentLoginBinding
import com.placefy.app.helpers.InputExtended
import com.placefy.app.models.signin.SignInRequest
import com.placefy.app.models.signin.SignInResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


class LoginFragment : Fragment() {

    private var showPass: Boolean = false

    private val base by lazy {
        RetrofitHelper().noAuthApi
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)

        setShowPassEvents(binding)
        setBtnSignInEvents(binding)
        setBtnSignUpEvents(binding)

        //TODO remover
        binding.email.setText("marcio.theo.92+16@gmail.com")
        binding.password.setText("Jmarcim@1234!")
//
//        binding.btnSignup.setOnClickListener {
//            Log.i("ad", "chegouaqui")
////            val ad = AlertDialog.Builder(context).create()
////
////
////            ad.setTitle("TITULO")
////            ad.setMessage("MENSAGEM")
////            ad.setButton("ok",
////                DialogInterface.OnClickListener { dialog, which ->
//            Toast.makeText(context, "Good", Toast.LENGTH_SHORT).show()
////                    dialog.dismiss()
////                })
////            ad.show()
//        }


        return binding.root
    }

    private fun setShowPassEvents(binding: FragmentLoginBinding) {
        binding.imgShowPass.setOnClickListener {
            showPass = !showPass
            binding.imgShowPass.setImageResource(if (showPass) R.drawable.ic_visibility_off_24 else R.drawable.ic_visibility_24)

            binding.password.inputType =
                if (showPass) InputType.TYPE_CLASS_TEXT else InputExtended.TYPE_PASSWORD_TEXT
        }
    }

    private fun setBtnSignUpEvents(binding: FragmentLoginBinding) {
        binding.btnSignUp.setOnClickListener {

            parentFragmentManager.commit {
                replace<SignUpFragment>(requireActivity().findViewById(R.id.fragmentContainerView))
            }
            SuccessDialog().show(parentFragmentManager, "success")

        }
    }

    private fun setBtnSignInEvents(binding: FragmentLoginBinding) {
        binding.btnSignIn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                signIn(binding)
            }
        }
    }

    private suspend fun signIn(binding: FragmentLoginBinding) {
        var result: Response<SignInResponse>? = null
        val data = SignInRequest(
            binding.email.text.toString(),
            binding.password.text.toString(),
            binding.keepConnected.isChecked
        )

        try {
            val api = base.create(LoginAPI::class.java)
            result = api.signIn(data)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("signIn", "erro ao recuperar")
        }


        if (result != null) {
            if (result.isSuccessful) {
                val post = result.body()
                Log.i("endereco:", "$post")
                withContext(Dispatchers.Main) { }
                startActivity(Intent(context, AdminActivity::class.java))
            } else {
                Log.i("endereco:", "$result")
            }


        }
    }
}