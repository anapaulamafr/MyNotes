package com.example.mynotes

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        val currentUser = auth.currentUser

        setSupportActionBar(binding.toolbar)
        navController = Navigation.findNavController(this, R.id.myNavHostFragment)
        navGraph = navController.navInflater.inflate(R.navigation.navigation)

        binding.txtLogout.setOnClickListener {
            Firebase.auth.signOut()
            Navigation.findNavController(this, R.id.myNavHostFragment).navigate(R.id.logInFragment)
        }

        if (currentUser == null) {
            binding.txtLogout.visibility = View.GONE
        }
        else {
            binding.txtLogout.visibility = View.VISIBLE
            Navigation.findNavController(this, R.id.myNavHostFragment).navigate(R.id.listaNotasFragment)
        }
    }
}