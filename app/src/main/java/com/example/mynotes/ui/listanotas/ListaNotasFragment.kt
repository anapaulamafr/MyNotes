package com.example.mynotes.ui.listanotas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.Nota
import com.example.mynotes.databinding.FragmentListaNotasBinding

class ListaNotasFragment : Fragment() {

    private var _binding: FragmentListaNotasBinding? = null
    private val binding get() = _binding!!
    private var servicoItemAdapter: RecyclerView.Adapter<NotaItemAdapter.ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaNotasBinding.inflate(inflater, container, false)

        val listaNotas = ArrayList<Nota>()
        val recyclerView = binding.recyclerViewListaNotas

        recyclerView.adapter = servicoItemAdapter
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = NotaItemAdapter(listaNotas, this.context)
        }

        binding.btnNovaNota.setOnClickListener {
            findNavController().navigate(ListaNotasFragmentDirections.actionListaNotasFragmentToNotaFragment())
        }

        val arrayList = ArrayList<Nota>()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}