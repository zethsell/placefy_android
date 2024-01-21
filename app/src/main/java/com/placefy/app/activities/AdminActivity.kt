package com.placefy.app.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.placefy.app.R
import com.placefy.app.api.RetrofitHelper
import com.placefy.app.database.dao.AuthDAO
import com.placefy.app.databinding.ActivityAdminBinding
import com.placefy.app.models.Auth


class AdminActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAdminBinding

    private val base by lazy {
        RetrofitHelper(this).noAuthApi
    }

    private val authDAO by lazy {
        AuthDAO(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.appBarAdmin.toolbar)
        setFloatButtonEvents(binding)
        setMenuEvents(binding)

        appBarConfiguration = setBar(binding)

        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_admin)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.admin, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_admin)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setFloatButtonEvents(binding: ActivityAdminBinding) {
        binding.appBarAdmin.fab.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    private fun setMenuEvents(binding: ActivityAdminBinding) {
        binding.appBarAdmin.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_settings -> {
                    logout()
                    return@setOnMenuItemClickListener true
                }

                else -> {
                    return@setOnMenuItemClickListener true
                }
            }

        }
    }

    private fun logout() {
        authDAO.save(Auth(1, "", "", false))

        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun setBar(binding: ActivityAdminBinding): AppBarConfiguration {
        return AppBarConfiguration(
            setOf(
                R.id.nav_users,
                R.id.nav_users_approve,
                R.id.nav_users,
                R.id.nav_plans,
                R.id.nav_properties
            ), binding.drawerLayout
        )
    }
}