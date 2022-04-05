package com.example.mynotes.ui.nota

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mynotes.databinding.FragmentNotaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NotaFragment : Fragment() {

    private var _binding: FragmentNotaBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPause() {
        super.onPause()

        auth = Firebase.auth
        val currentUser = auth.currentUser

        val texto: String = binding.editTextTextMultiLine.text.toString()
        if (texto != "") {
            val db = Firebase.firestore
            val dbUser = db.collection("users")
            db.document(currentUser!!.uid)
        }
    }
}