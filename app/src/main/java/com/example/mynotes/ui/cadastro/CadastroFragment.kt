package com.example.mynotes.ui.cadastro

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mynotes.Nota
import com.example.mynotes.R
import com.example.mynotes.Usuario
import com.example.mynotes.databinding.FragmentCadastroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class CadastroFragment : Fragment() {

    private var _binding: FragmentCadastroBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCadastroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        firebaseFirestore = FirebaseFirestore.getInstance()

        with (binding) {
            buttonSignUp.setOnClickListener {
                val email = binding.editTextEmail.text.toString()
                val password = binding.editTextSenha.text.toString()
                createAccount(email, password)
            }
        }
    }

    private fun createAccount(email: String, password: String) {
        if (!validateForm()) {
            return
        }



        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                val isNewUser: Boolean = task.result.additionalUserInfo!!.isNewUser
                if (task.isSuccessful && isNewUser) {
                    val notas = ArrayList<Nota>()
                    val user = auth.currentUser
                    val usuario = Usuario(user!!.uid, user.displayName, notas)
                    firebaseFirestore.collection("users").document(user.uid).set(usuario)
                    findNavController().navigate(R.id.logInFragment)
                }
            }
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = binding.editTextEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            binding.editTextEmail.error = "Required."
            valid = false
        } else {
            binding.editTextEmail.error = null
        }

        val password = binding.editTextSenha.text.toString()
        if (TextUtils.isEmpty(password)) {
            binding.editTextSenha.error = "Required."
            valid = false
        } else {
            binding.editTextSenha.error = null
        }

        return valid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}