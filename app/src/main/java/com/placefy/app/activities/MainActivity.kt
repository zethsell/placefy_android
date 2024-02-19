package com.placefy.app.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import com.placefy.app.R
import com.placefy.app.adapters.PropertyAdapter
import com.placefy.app.api.RetrofitHelper
import com.placefy.app.api.interfaces.PropertyAPI
import com.placefy.app.database.dao.AuthDAO
import com.placefy.app.databinding.ActivityMainBinding
import com.placefy.app.models.data.Property
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val base by lazy {
        RetrofitHelper(this).noAuthApi
    }

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
        setSBtnLoginEvents()
        setSupportActionBar(binding.includeToolbar.publicToolbar)
        loadProperties()
    }


    private fun loadProperties() {
        CoroutineScope(Dispatchers.IO).launch {
            val api = base.create(PropertyAPI::class.java)
            val response: Response<Array<Property>> = api.list()
            val result: Array<Property> = response.body() ?: throw Exception("Falha no login")

            withContext(Dispatchers.Main) {
                val adapter = PropertyAdapter(baseContext)
                adapter.loadData(result.toMutableList())
                binding.rvProperties.adapter = adapter
                binding.rvProperties.layoutManager = LinearLayoutManager(baseContext)
            }
        }
    }


    private fun setSBtnLoginEvents() {
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