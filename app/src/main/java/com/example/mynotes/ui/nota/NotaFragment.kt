package com.example.mynotes.ui.nota

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import com.example.mynotes.Nota
import com.example.mynotes.databinding.FragmentNotaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class NotaFragment : Fragment() {

    private var _binding: FragmentNotaBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore
    private var notaParaEditar: Boolean = false
    lateinit var notaAntiga: Nota

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotaBinding.inflate(inflater, container, false)

        val bundle: Bundle = this.requireArguments()

        if (bundle.containsKey("notaTexto")) {
            val notaExistente = bundle.getString("notaTexto")
            notaParaEditar = true
            notaAntiga = Nota(notaExistente!!)
            binding.editTextTextMultiLine.setText(notaExistente)
            bundle.remove("notaTexto")
        }

        firebaseFirestore = FirebaseFirestore.getInstance()
        auth = Firebase.auth

        binding.btnSalvarNota.setOnClickListener {
            salvarNota()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun salvarNota() {
        auth = Firebase.auth
        val mensagem = binding.editTextTextMultiLine.text.toString()

        val texto: String = binding.editTextTextMultiLine.text.toString()
        if (texto != "") {
            editarNota(notaAntiga, notaNova = Nota(mensagem))
        }
        else {
            val currentUser = auth.currentUser
            val dbUser =  firebaseFirestore.collection("users").document(currentUser!!.uid)
            dbUser.update("notas", FieldValue.arrayUnion(Nota(mensagem)))
        }
    }

    fun editarNota(notaAntiga: Nota, notaNova: Nota) {
        val currentUser = auth.currentUser
        val dbUser =  firebaseFirestore.collection("users").document(currentUser!!.uid)
        dbUser.update("notas", FieldValue.arrayRemove(notaAntiga))
        dbUser.update("notas", FieldValue.arrayUnion(notaNova))
    }
}