package com.example.mynotes.ui.nota

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mynotes.databinding.FragmentNotaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NotaFragment : Fragment() {

    private var _binding: FragmentNotaBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotaBinding.inflate(inflater, container, false)

        firebaseFirestore = FirebaseFirestore.getInstance()
        auth = Firebase.auth

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPause() {
        super.onPause()

        auth = Firebase.auth
        val mensagem = binding.editTextTextMultiLine.text.toString()

        val texto: String = binding.editTextTextMultiLine.text.toString()
        if (texto != "") {
            val currentUser = auth.currentUser
            val dbUser =  firebaseFirestore.collection("users").document(currentUser!!.uid)
            dbUser.update("notas", FieldValue.arrayUnion(mensagem))


        }
    }
}