package com.placefy.app.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.placefy.app.R
import com.placefy.app.database.dao.AuthDAO
import com.placefy.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val authDAO by lazy {
        AuthDAO(baseContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        verifyToken()
        setSBtnLoginEvents(binding)
        setSupportActionBar(binding.includeToolbar.publicToolbar)
    }

    private fun setSBtnLoginEvents(binding: ActivityMainBinding) {
        binding.includeToolbar.btnLogin.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    if (authDAO.show().accessToken.toString() != "")
                        AdminActivity::class.java else AuthActivity::class.java
                )
            )
        }
    }

    private fun verifyToken() {
        if (authDAO.show().accessToken.toString() != "") {
            binding.includeToolbar.btnLogin.setText("ADMIN")
            val button = binding.includeToolbar.btnLogin
            button.setCompoundDrawablesWithIntrinsicBounds(
                AppCompatResources.getDrawable(this, R.drawable.ic_home_24),
                null,
                null,
                null
            )
        }
    }
}