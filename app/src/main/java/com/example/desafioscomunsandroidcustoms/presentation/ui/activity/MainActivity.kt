package com.example.desafioscomunsandroidcustoms.presentation.ui.activity

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.desafioscomunsandroidcustoms.R
import com.example.desafioscomunsandroidcustoms.databinding.ActivityMainBinding
import com.example.desafioscomunsandroidcustoms.util.setVisible
import com.example.desafioscomunsandroidcustoms.util.viewBinding

class MainActivity : AppCompatActivity(), ActivityCallback {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    private val navController by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_container)!!.findNavController()
    }
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        setContentView(binding.root)
        
        setNavigation()
        setupToolbar()
        setupBottomNavigation()
        setSplashScreen()
    }

    private fun setNavigation() {
        navController.graph.setStartDestination(R.id.mainFragment)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.mainFragment))
    }

    private fun setupToolbar(){
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

    private fun setupBottomNavigation() =
        binding.bottomNavigation.apply { setupWithNavController(navController) }

    private fun setSplashScreen() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnDrawListener {
            object : ViewTreeObserver.OnPreDrawListener {
                val delay = 500L //Maximo possivel pela api 1s android 12
                override fun onPreDraw(): Boolean {
                    Thread.sleep(delay)
                    content.viewTreeObserver.removeOnPreDrawListener(this)
                    return true
                }
            }
        }
    }

    override fun showAppBarBackButton(show: Boolean) {
        supportActionBar?.setHomeButtonEnabled(show)
        supportActionBar?.setDisplayHomeAsUpEnabled(show)
        supportActionBar?.setDisplayShowHomeEnabled(show)
    }

    override fun showAppBarTitle(show: Boolean) {
        supportActionBar?.setDisplayShowTitleEnabled(show)
    }

    override fun showActionBar(show: Boolean) {
        if (show) supportActionBar?.show() else supportActionBar?.hide()
    }

    override fun showProgressBar() {
        binding.progress.setVisible(true)
    }

    override fun hideProgressBar() {
        binding.progress.setVisible(false)
    }
}