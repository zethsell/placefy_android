package com.placefy.app.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.placefy.app.R
import com.placefy.app.databinding.ActivityFormBinding

class FormActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
    }

    private fun initToolbar() {
        binding.includeFormToolbar.clMain.visibility = View.GONE
        binding.includeFormToolbar.formToolbar.title = "Upload de video"
        binding.includeFormToolbar.formToolbar.setTitleTextColor(
            ContextCompat.getColor(
                this,
                R.color.primary
            )
        )
        setSupportActionBar(binding.includeFormToolbar.formToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}