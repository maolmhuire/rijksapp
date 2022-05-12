package com.maolmhuire.rijksapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.maolmhuire.rijksapp.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_RijksApp)
        super.onCreate(savedInstanceState)
        with(MainActivityBinding.inflate(layoutInflater)) {
            setContentView(root)
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            val appBarConfiguration = AppBarConfiguration(navController.graph)
            mainToolbar.setupWithNavController(navController, appBarConfiguration)
            setSupportActionBar(mainToolbar)
        }
    }

}