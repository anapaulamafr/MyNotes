package com.example.mynotes.ui.listanotas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.FirebaseFirestoreHelper
import com.example.mynotes.Nota
import com.example.mynotes.databinding.FragmentListaNotasBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ListaNotasFragment() : Fragment() {

    private var _binding: FragmentListaNotasBinding? = null
    private val binding get() = _binding!!
    private var notaItemAdapter: RecyclerView.Adapter<NotaItemAdapter.ViewHolder>? = null
    private lateinit var firebaseFirestoreHelper: FirebaseFirestoreHelper
    val firebaseFirestore = Firebase.firestore
    private val firebaseUser = FirebaseAuth.getInstance().currentUser


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaNotasBinding.inflate(inflater, container, false)

        firebaseFirestoreHelper = FirebaseFirestoreHelper()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val notas = ArrayList<Nota>()

        firebaseFirestore.collection("notas-${firebaseUser!!.uid}")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var member =
                        Nota(document.data["id"].toString(), document.data["conteudo"].toString())
                    notas.add(member)
                }
                val recyclerView = binding.recyclerViewListaNotas
                recyclerView.adapter = notaItemAdapter
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(this.context)
                    adapter = NotaItemAdapter(notas)
                }
            }

        binding.btnNovaNota.setOnClickListener {
            findNavController().navigate(
                ListaNotasFragmentDirections.actionListaNotasFragmentToNotaFragment(
                    bundle = Bundle()
                )
            )
        }
    }
}