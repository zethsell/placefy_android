package com.placefy.app.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.placefy.app.R
import com.placefy.app.api.RetrofitHelper
import com.placefy.app.api.interfaces.UserAPI
import com.placefy.app.database.dao.AuthDAO
import com.placefy.app.database.dao.UserDAO
import com.placefy.app.databinding.ActivityAdminBinding
import com.placefy.app.enums.UserType
import com.placefy.app.helpers.CircleTransform
import com.placefy.app.models.data.user.User
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


class AdminActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val binding by lazy {
        ActivityAdminBinding.inflate(layoutInflater)
    }

    private val base by lazy {
        RetrofitHelper(this).authApi
    }

    private val authDAO by lazy {
        AuthDAO(this)
    }

    private val userDAO by lazy {
        UserDAO(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        setSupportActionBar(binding.appBarAdmin.toolbar)
        setFloatButtonEvents()
        setMenuEvents()


        loadLoggedUser()

        appBarConfiguration = setBar()

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

    private fun setFloatButtonEvents() {
        binding.appBarAdmin.fab.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    private fun setBarVisibility(user: User) {
        val menu = binding.navView.menu

        if (user.type == UserType.ADMIN.type) {
            menu.findItem(R.id.usersMenu).isVisible = true
            menu.findItem(R.id.etcMenu).isVisible = true
            menu.findItem(R.id.configMenu).isVisible = false
        } else {
            menu.findItem(R.id.usersMenu).isVisible = false
            menu.findItem(R.id.etcMenu).isVisible = false
            menu.findItem(R.id.configMenu).isVisible = true
        }
    }

    private fun setMenuEvents() {
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
        authDAO.clean()
        userDAO.clean()

        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun setBar(): AppBarConfiguration {

        val user = userDAO.me()

        user?.let { setBarVisibility(it) }

        return AppBarConfiguration(
            setOf(
                R.id.nav_users,
                R.id.nav_users_approve,
                R.id.nav_users,
                R.id.nav_plans,
                R.id.nav_properties,
                R.id.navMyPlans,
                R.id.navMyProperties,
            ), binding.drawerLayout
        )
    }

    private fun loadLoggedUser() {
        CoroutineScope(Dispatchers.IO).launch {
            var user = userDAO.me()

            if (user == null) {
                val api = base.create(UserAPI::class.java)
                val response: Response<User> = api.me()
                user = response.body()
            }

            withContext(Dispatchers.Main) {
                val navHeader = binding.navView.getHeaderView(0)
                val navName = navHeader.findViewById<TextView>(R.id.navHeaderAdminName)
                val navEmail = navHeader.findViewById<TextView>(R.id.navHeaderAdminEmail)
                val navImage = navHeader.findViewById<ImageView>(R.id.navHeaderImg)

                if (user != null) {
                    navName.text = user.name
                    navEmail.text = user.email

                    Picasso.get()
                        .load(user.imgProfileThumb)
                        .resize(300, 300)
                        .centerInside()
                        .transform(CircleTransform())
                        .into(navImage)

                    userDAO.save(user)
                }
            }
        }
    }
}