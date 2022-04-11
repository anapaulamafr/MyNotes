package com.example.mynotes.ui.listanotas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.FirebaseFirestoreHelper
import com.example.mynotes.R
import com.example.mynotes.databinding.FragmentListaNotasBinding
import java.nio.channels.Selector
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ListaNotasFragment() : Fragment() {

    private var _binding: FragmentListaNotasBinding? = null
    private val binding get() = _binding!!
    private var notaItemAdapter: RecyclerView.Adapter<NotaItemAdapter.ViewHolder>? = null
    private lateinit var firebaseFirestore: FirebaseFirestoreHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaNotasBinding.inflate(inflater, container, false)

        firebaseFirestore = FirebaseFirestoreHelper()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            val listaNotas = firebaseFirestore.getNotas()
            val recyclerView = binding.recyclerViewListaNotas

            recyclerView.adapter = notaItemAdapter
            recyclerView.apply {
                layoutManager = LinearLayoutManager(this.context)
                adapter = NotaItemAdapter(listaNotas, this.context)
            }

            binding.btnNovaNota.setOnClickListener {
                findNavController().navigate(ListaNotasFragmentDirections.actionListaNotasFragmentToNotaFragment())
            }
        }
    }
}