package com.example.mynotes.ui.nota

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mynotes.FirebaseFirestoreHelper
import com.example.mynotes.Nota
import com.example.mynotes.R
import com.example.mynotes.databinding.FragmentNotaBinding
import com.example.mynotes.ui.listanotas.SharedViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class NotaFragment : Fragment() {

    private var _binding: FragmentNotaBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    val firestore = FirebaseFirestoreHelper()
    lateinit var notaExistente: Nota
    private val args by navArgs<NotaFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotaBinding.inflate(inflater, container, false)

        if (args.nota != "") {
            notaExistente = Nota(args.nota)
            binding.editTextTextMultiLine.setText(notaExistente.conteudo)
        } else {
            notaExistente = Nota("")
        }

        binding.btnSalvarNota.setOnClickListener {
            if (notaExistente != Nota("")) {
                salvarNota()
            }
            else {
                editarNota(notaExistente)
            }
            findNavController().navigate(R.id.listaNotasFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun salvarNota() {
        auth = Firebase.auth
        val nota = Nota(binding.editTextTextMultiLine.text.toString())

        firestore.criarNota(nota)
    }

    fun editarNota(notaAntiga: Nota) {
        val notaNova = Nota(binding.editTextTextMultiLine.text.toString())

        firestore.excluirNota(notaAntiga)
        firestore.criarNota(notaNova)
    }
}