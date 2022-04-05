package com.example.mynotes

import android.os.Bundle
import android.view.View
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
import com.google.firebase.firestore.ktx.firestore
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

        auth = Firebase.auth
        val currentUser = auth.currentUser

        val db = Firebase.firestore

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

        toolbar.navigationIcon = null

        binding.txtLogout.setOnClickListener {
            Firebase.auth.signOut()
        }

        if (currentUser == null) {
            binding.txtLogout.visibility = View.GONE
            binding.txtToolbarTitle.visibility = View.VISIBLE
            Navigation.findNavController(this, R.id.myNavHostFragment).navigate(R.id.logInFragment)
        }
        else {
            val dbUser = db.collection("users")
            db.document(currentUser.uid)
            binding.txtLogout.visibility = View.VISIBLE
            binding.txtToolbarTitle.visibility = View.GONE
        }

    }
}