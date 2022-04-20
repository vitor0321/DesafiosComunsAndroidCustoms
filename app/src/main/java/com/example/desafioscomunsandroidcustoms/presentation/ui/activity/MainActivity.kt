package com.example.desafioscomunsandroidcustoms.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.ActivityMainBinding
import com.example.desafioscomunsandroidcustoms.util.viewBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_container) as NavHostFragment
        navController = navHostFragment.navController
        navController.graph.setStartDestination(R.id.mainFragment)
        supportActionBar?.hide()
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.mainFragment)
        )

        binding.apply {
            toolbarApp.setupWithNavController(navController, appBarConfiguration)
            navController.addOnDestinationChangedListener { _, destination, _ ->
                val isTopLeveDestination = appBarConfiguration
                    .topLevelDestinations.contains(destination.id)
                if (!isTopLeveDestination) {
                    toolbarApp.setNavigationIcon(R.drawable.ic_back)
                }
            }
        }
    }
}