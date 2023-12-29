package com.placefy.app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.placefy.app.R
import com.placefy.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.includeToolbar.btnLogin.setOnClickListener {
            startActivity(Intent(this, AuthActivity::class.java))
        }

        setSupportActionBar(binding.includeToolbar.publicToolbar)
    }


}