package com.example.mynotes.ui.nota

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mynotes.FirebaseFirestoreHelper
import com.example.mynotes.Nota
import com.example.mynotes.R
import com.example.mynotes.databinding.FragmentNotaBinding
import com.google.firebase.auth.FirebaseAuth


class NotaFragment : Fragment() {

    private var _binding: FragmentNotaBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    val firestore = FirebaseFirestoreHelper()
    var notaExistente: Nota? = null
    private val args by navArgs<NotaFragmentArgs>()
    private val bundle by lazy {
        try {
            args.bundle
        } catch (_: Exception) {
            requireArguments()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotaBinding.inflate(inflater, container, false)

        if (bundle.containsKey("ID")) {
            val id = bundle.getString("ID")!!
            val conteudo = bundle.getString("CONTEUDO")!!
            notaExistente = Nota(id, conteudo)
            binding.editTextTextMultiLine.setText(notaExistente!!.conteudo)
        }

        binding.btnSalvarNota.setOnClickListener {
            if (notaExistente == null) {
                firestore.criarNota(binding.editTextTextMultiLine.text.toString())
            }
            else {
                firestore.editarNota(binding.editTextTextMultiLine.text.toString(), notaExistente!!)
            }
            findNavController().navigate(R.id.listaNotasFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}