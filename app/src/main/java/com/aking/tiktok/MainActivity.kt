package com.aking.tiktok

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.aking.tiktok.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment?)!!
        val navController = navHostFragment.navController

        binding.appBarMain.bottomNavView.setupWithNavController(navController)

        binding.appBarMain.bottomNavView.menu.findItem(R.id.placeholder).setOnMenuItemClickListener {
            Snackbar.make(binding.appBarMain.innerContainer, "发布", Snackbar.LENGTH_LONG).show()
            true
        }
    }
}