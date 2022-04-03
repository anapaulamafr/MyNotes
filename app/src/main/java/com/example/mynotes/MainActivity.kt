package com.example.mynotes

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.mynotes.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navGraph: NavGraph
    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        navController = Navigation.findNavController(this, R.id.myNavHostFragment)
        navGraph = navController.navInflater.inflate(R.navigation.navigation)
        navGraph.setStartDestination(R.id.cadastroFragment)
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.myNavHostFragment
        ) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        auth = Firebase.auth
        val currentUser = auth.currentUser
        val palavra = currentUser!!.email

        if (currentUser == null) {
            Navigation.findNavController(this, R.id.myNavHostFragment).navigate(R.id.logInFragment)
        }
    }
}