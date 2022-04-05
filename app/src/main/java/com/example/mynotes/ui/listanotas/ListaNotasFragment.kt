package com.example.mynotes.ui.listanotas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mynotes.Nota
import com.example.mynotes.R
import com.example.mynotes.databinding.FragmentListaNotasBinding

class ListaNotasFragment : Fragment() {

    private var _binding: FragmentListaNotasBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaNotasBinding.inflate(inflater, container, false)

        binding.btnNovaNota.setOnClickListener {
            findNavController().navigate(R.id.notaFragment)
        }

        val arrayList = ArrayList<Nota>()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}